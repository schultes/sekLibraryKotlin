package de.thm.tp.library.firebase.authentication

import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

/**
 * This class provides methods for authentication of a user
 */
class TPFirebaseAuthentication {
    companion object {

        /**
         * Logging in a user with the help of his credentials
         *
         * @param email     User email address
         * @param password  User password
         * @param callback  If successful, the user is returned, otherwise an error message.
         *
         * @see TPFirebaseAuthenticationUser
         */
        fun signIn(
            email: String,
            password: String,
            callback: (user: TPFirebaseAuthenticationUser?, error: String?) -> Unit
        ) {
            Firebase.auth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    val firebaseUser = it.user!!
                    callback(
                        TPFirebaseAuthenticationUser(
                            firebaseUser.uid,
                            firebaseUser.email!!,
                            firebaseUser.displayName,
                            firebaseUser.isEmailVerified
                        ), null
                    )
                }
                .addOnFailureListener {
                    callback(null, it.localizedMessage)
                }
        }

        /**
         * Registering a new user with the help of an email address and a password
         *
         * @param email     User email address
         * @param password  User password
         * @param callback  If successful, the user is returned, otherwise an error message.
         *
         * @see TPFirebaseAuthenticationUser
         */
        fun signUp(
            email: String,
            password: String,
            callback: (user: TPFirebaseAuthenticationUser?, error: String?) -> Unit
        ) {
            Firebase.auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    val firebaseUser = it.user!!
                    callback(
                        TPFirebaseAuthenticationUser(
                            firebaseUser.uid,
                            firebaseUser.email!!,
                            firebaseUser.displayName,
                            firebaseUser.isEmailVerified
                        ), null
                    )
                }
                .addOnFailureListener {
                    callback(null, it.localizedMessage)
                }
        }

        /**
         * Registering a new user using an email address, password and display name
         *
         * @param email         User email address
         * @param password      User password
         * @param displayName   Display name
         * @param callback      If successful, the user is returned, otherwise an error message.
         *
         * @see TPFirebaseAuthenticationUser
         */
        fun signUp(
            email: String,
            password: String,
            displayName: String,
            callback: (user: TPFirebaseAuthenticationUser?, error: String?) -> Unit
        ) {
            Firebase.auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener { outerLambda ->
                    val firebaseUser = outerLambda.user!!
                    firebaseUser.updateProfile(
                        UserProfileChangeRequest.Builder().setDisplayName(displayName).build()
                    )
                        .addOnSuccessListener {
                            callback(
                                TPFirebaseAuthenticationUser(
                                    firebaseUser.uid,
                                    firebaseUser.email!!,
                                    displayName,
                                    firebaseUser.isEmailVerified
                                ), null
                            )
                        }
                        .addOnFailureListener { innerLambda ->
                            callback(
                                TPFirebaseAuthenticationUser(
                                    firebaseUser.uid,
                                    firebaseUser.email!!,
                                    null,
                                    firebaseUser.isEmailVerified
                                ), innerLambda.localizedMessage
                            )
                        }
                }
                .addOnFailureListener { outerLambda ->
                    callback(null, outerLambda.localizedMessage)
                }
        }

        /**
         * Logs out logged in user
         */
        fun signOut() {
            Firebase.auth.signOut()
        }

        /**
         * Returns the currently logged in user,
         * if no one is logged in, zero is returned
         *
         * @return TPFirebaseAuthenticationUser?
         *
         * @see TPFirebaseAuthenticationUser
         */
        fun getUser(): TPFirebaseAuthenticationUser? {
            val firebaseUser = Firebase.auth.currentUser
            return if (firebaseUser == null) {
                null;
            } else {
                TPFirebaseAuthenticationUser(
                    firebaseUser.uid,
                    firebaseUser.email!!,
                    firebaseUser.displayName,
                    firebaseUser.isEmailVerified
                )
            }
        }

        /**
         * Updates the email address of the currently logged in user
         *
         * @param email         User email address
         * @param callback      In case of error, an error message is returned.
         */
        fun updateCurrentUserEmail(
            email: String,
            callback: (error: String?) -> Unit
        ) {
            if (isSignedIn()) {
                Firebase.auth.currentUser!!.updateEmail(email)
                    .addOnSuccessListener {
                        callback(null)
                    }
                    .addOnFailureListener {
                        callback(it.localizedMessage)
                    }
            } else {
                callback("No user signed in")
            }
        }

        /**
         * Updates the display name of the currently logged in user
         *
         * @param displayName   Display name
         * @param callback      In case of an error, an error message is returned.
         */
        fun updateCurrentUserDisplayName(
            displayName: String,
            callback: (error: String?) -> Unit
        ) {
            if (isSignedIn()) {
                Firebase.auth.currentUser!!.updateProfile(
                    UserProfileChangeRequest.Builder()
                        .setDisplayName(displayName).build()
                )
                    .addOnSuccessListener {
                        callback(null)
                    }
                    .addOnFailureListener {
                        callback(it.localizedMessage)
                    }
            } else {
                callback("No user signed in")
            }
        }

        /**
         * Deletes the currently logged in user and logs him out
         *
         * @param callback  In case of an error, an error message is returned.
         */
        fun deleteUser(
            callback: (error: String?) -> Unit
        ) {
            if (isSignedIn()) {
                Firebase.auth.currentUser!!.delete()
                    .addOnSuccessListener {
                        callback(null)
                    }
                    .addOnFailureListener {
                        callback(it.localizedMessage)
                    }
            } else {
                callback("No user signed in")
            }
        }

        /**
         * Checks if a user is currently logged in
         *
         * @return Boolean
         */
        fun isSignedIn(): Boolean {
            return getUser() != null
        }

        /**
         * Sends a message for verification to the email address of the currently logged in user
         *
         * @param callback  In case of an error, an error message is returned.
         */
        fun sendEmailVerification(
            callback: (error: String?) -> Unit
        ) {
            if (isSignedIn()) {
                Firebase.auth.currentUser!!.sendEmailVerification()
                callback(null)
            } else {
                callback("No user signed in")
            }
        }

        /**
         * Sends a password reset message to the email address of the currently logged in user
         *
         * @param callback  In case of an error, an error message is returned.
         */
        fun sendPasswordResetEmail(
            callback: (error: String?) -> Unit
        ) {
            if (isSignedIn()) {
                Firebase.auth.sendPasswordResetEmail(getUser()!!.email)
                callback(null)
            } else {
                callback("No user signed in")
            }
        }
    }
}
