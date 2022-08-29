package de.thm.tp.library.firebase.firestore

/**
 * In addition to the ID of the document and the name of the associated collection, this class contains,
 * also the stored data of the document itself
 *
 * @param documentId ID of the document
 * @param collectionName Name of the associated collection
 * @param data The stored data of the document as key-value pairs.
 */
data class TPFirebaseFirestoreDocument(
    val documentId: String,
    val collectionName: String,
    val data: Map<String, Any>
)
