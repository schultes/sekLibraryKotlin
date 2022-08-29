package de.thm.tp.library.firebase.authentication

/**
 * This class holds the data of the logged in user
 *
 * @param uid                The unique ID of the user
 * @param email              The user's email address
 * @param displayName        The display name of the user (not unique)
 * @param isEmailVerified    Indicates whether the user has verified their email address
 */
class TPFirebaseAuthenticationUser internal constructor(
    val uid: String,
    val email: String,
    val displayName: String?,
    val isEmailVerified: Boolean
)
