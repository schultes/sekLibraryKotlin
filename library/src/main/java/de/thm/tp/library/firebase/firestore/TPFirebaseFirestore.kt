package de.thm.tp.library.firebase.firestore

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

/**
 * This class provides static methods for using Firestore.
 */
class TPFirebaseFirestore {
    companion object {

        /**
         * Inserts a new document with the specified data into the specified collection.
         *
         * @param collectionName Name of the collection
         * @param data The data to insert as key-value pairs
         * @param callback If successful, the created document is returned, otherwise an error message.
         *
         * @see TPFirebaseFirestoreDocument
         */
        fun addDocument(
            collectionName: String,
            data: Map<String, Any>,
            callback: (result: TPFirebaseFirestoreDocument?, error: String?) -> Unit
        ) {
            Firebase.firestore.collection(collectionName).add(data)
                .addOnSuccessListener {
                    getDocument(collectionName, it.id) { result, error ->
                        callback(result, error)
                    }
                }
                .addOnFailureListener {
                    callback(null, it.localizedMessage)
                }
        }

        /**
         * Creates or overwrites a document with the specified ID and data in the specified collection.
         *
         * @param collectionName Name of the collection
         * @param documentId ID of the document
         * @param data The data to insert as key-value pairs
         * @param callback In case of error, an error message is returned.
         */
        fun setDocument(
            collectionName: String,
            documentId: String,
            data: Map<String, Any>,
            callback: (error: String?) -> Unit
        ) {
            Firebase.firestore.collection(collectionName).document(documentId).set(data)
                .addOnSuccessListener {
                    callback(null)
                }
                .addOnFailureListener {
                    callback(it.localizedMessage)
                }
        }

        /**
         * Returns the document with the requested ID from the specified collection.
         *
         * @param collectionName Name of the collection
         * @param documentId ID of the document
         * @param callback If successful, the requested document is returned, otherwise an error message.
         *
         * @see TPFirebaseFirestoreDocument
         */
        fun getDocument(
            collectionName: String,
            documentId: String,
            callback: (result: TPFirebaseFirestoreDocument?, error: String?) -> Unit
        ) {
            val documentReference =
                Firebase.firestore.collection(collectionName).document(documentId)
            documentReference.get()
                .addOnSuccessListener {
                    callback(
                        TPFirebaseFirestoreDocument(
                            documentId,
                            collectionName,
                            it.data ?: mapOf()
                        ), null
                    )
                }
                .addOnFailureListener {
                    callback(null, it.localizedMessage)
                }
        }

        /**
         * Returns all documents in the specified collection.
         *
         * @param collectionName Name of the collection.
         * @param callback If successful, a list of all documents of the specified collection is returned, otherwise an error message.
         *
         * @see TPFirebaseFirestoreDocument
         */
        fun getDocuments(
            collectionName: String,
            callback: (result: ArrayList<TPFirebaseFirestoreDocument>?, error: String?) -> Unit
        ) {
            Firebase.firestore.collection(collectionName).get()
                .addOnSuccessListener {
                    val array = arrayListOf<TPFirebaseFirestoreDocument>()
                    for (document in it.documents) {
                        array.add(
                            TPFirebaseFirestoreDocument(
                                document.id,
                                collectionName,
                                document.data ?: mapOf()
                            )
                        )
                    }
                    callback(array, null)
                }
                .addOnFailureListener {
                    callback(null, it.localizedMessage)
                }
        }

        /**
         * Returns all documents that match the set criteria of the 'query' filter.
         *
         * @param queryBuilder Instance of TPFirebaseFirestoreQueryBuilder which sets filter criteria.
         * @param callback If successful, a list of documents matching the filter criteria is returned. Otherwise an error message.
         *
         * @see TPFirebaseFirestoreQueryBuilder
         * @see TPFirebaseFirestoreDocument
         */
        fun getDocuments(
            queryBuilder: TPFirebaseFirestoreQueryBuilder,
            callback: (result: ArrayList<TPFirebaseFirestoreDocument>?, error: String?) -> Unit
        ) {
            queryBuilder.getQuery().get()
                .addOnSuccessListener {
                    val array = arrayListOf<TPFirebaseFirestoreDocument>()
                    for (document in it.documents) {
                        array.add(
                            TPFirebaseFirestoreDocument(
                                document.id,
                                queryBuilder.getCollectionName(),
                                document.data ?: mapOf()
                            )
                        )
                    }
                    callback(array, null)
                }
                .addOnFailureListener {
                    callback(null, it.localizedMessage)
                }
        }

        /**
         * Updates the fields contained in `data` of the document with the ID `documentId`. If the document does not exist, an error is returned.
         *
         * @param collectionName Name of the collection
         * @param documentId ID of the document
         * @param data The updated data as key-value pairs
         * @param callback In case of error, an error message is returned.
         */
        fun updateDocument(
            collectionName: String,
            documentId: String,
            data: Map<String, Any>,
            callback: ((error: String?) -> Unit)? = null
        ) {
            Firebase.firestore.collection(collectionName).document(documentId).update(data)
                .addOnSuccessListener {
                    if (callback != null) {
                        callback(null)
                    }
                }
                .addOnFailureListener {
                    if (callback != null) {
                        callback(it.localizedMessage)
                    }
                }
        }

        /**
         * Deletes the document with the specified ID from the specified collection.
         *
         * @param collectionName Name of the collection
         * @param documentId ID of the document
         * @param callback In case of error an error message is returned.
         */
        fun deleteDocument(
            collectionName: String,
            documentId: String,
            callback: ((error: String?) -> Unit)? = null
        ) {
            Firebase.firestore.collection(collectionName).document(documentId).delete()
                .addOnSuccessListener {
                    if (callback != null) {
                        callback(null)
                    }
                }
                .addOnFailureListener {
                    if (callback != null) {
                        callback(it.localizedMessage)
                    }
                }
        }

        /**
         * Adds a SnapshotListener to a specified collection, which uses the callback to inform about changes in the collection.
         *
         * @param collectionName Name of the collection
         * @param callback When the SnapshotListener is added and changes occur later, a list of all documents in the collection is returned. In case of error, an error message is returned.
         *
         * @see TPFirebaseFirestoreDocument
         */
        fun addCollectionSnapshotListener(
            collectionName: String,
            callback: (result: ArrayList<TPFirebaseFirestoreDocument>?, error: String?) -> Unit
        ) {
            Firebase.firestore.collection(collectionName).addSnapshotListener { value, error ->
                if (value != null) {
                    val array = arrayListOf<TPFirebaseFirestoreDocument>()
                    for (document in value.documents) {
                        array.add(
                            TPFirebaseFirestoreDocument(
                                document.id,
                                collectionName,
                                document.data ?: mapOf()
                            )
                        )
                    }
                    callback(array, null)
                } else {
                    callback(null, error?.localizedMessage)
                }
            }
        }

        /**
         * Adds a SnapshotListener to a collection specified in the QueryBuilder, which uses the callback to inform about changes in the collection that match the set criteria of the 'query' filter.
         *
         * @param queryBuilder Instance of TPFirebaseFirestoreQueryBuilder that sets filter criteria.
         * @param callback When the SnapshotListener is added and changes occur later, a list of all documents in the collection that match the filter criteria is returned. In case of error, an error message is returned.
         *
         * @see TPFirebaseFirestoreQueryBuilder
         * @see TPFirebaseFirestoreDocument
         */
        fun addCollectionSnapshotListener(
            queryBuilder: TPFirebaseFirestoreQueryBuilder,
            callback: (result: ArrayList<TPFirebaseFirestoreDocument>?, error: String?) -> Unit
        ) {
            queryBuilder.getQuery().addSnapshotListener { value, error ->
                if (value != null) {
                    val array = arrayListOf<TPFirebaseFirestoreDocument>()
                    for (document in value.documents) {
                        array.add(
                            TPFirebaseFirestoreDocument(
                                document.id,
                                queryBuilder.getCollectionName(),
                                document.data ?: mapOf()
                            )
                        )
                    }
                    callback(array, null)
                } else {
                    callback(null, error?.localizedMessage)
                }
            }
        }

        /**
         * Adds a SnapshotListener to a document with the specified ID from the specified collection, which uses the callback to inform about changes to the document.
         *
         * @param collectionName Name of the collection
         * @param documentId ID of the document
         * @param callback When the SnapshotListener is added and changes occur later, the document is returned. In case of error, an error message is returned.
         *
         * @see TPFirebaseFirestoreDocument
         */
        fun addDocumentSnapshotListener(
            collectionName: String,
            documentId: String,
            callback: (result: TPFirebaseFirestoreDocument?, error: String?) -> Unit
        ) {
            Firebase.firestore.collection(collectionName).document(documentId)
                .addSnapshotListener { value, error ->
                    if (value != null) {
                        callback(
                            TPFirebaseFirestoreDocument(
                                value.id,
                                collectionName,
                                value.data ?: mapOf()
                            ), null
                        )
                    } else {
                        callback(null, error?.localizedMessage)
                    }
                }
        }
    }
}
