package de.thm.tp.library.firebase.firestore

import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

/**
 * This class can be used to compose a query for Firestore.
 * By means of this query, requested documents can be narrowed down in their scope.
 *
 * @param collectionName The name of the collection of Firestore.
 */
class TPFirebaseFirestoreQueryBuilder(private val collectionName: String) {

    private var query: Query

    init {
        this.query = Firebase.firestore.collection(collectionName)
    }

    /**
    * Filters a collection for documents,
     * where the array named `field` contains the value `value`.
     *
     * @param field Attribute name of the array.
     * @param value Value that must be contained in the array.
     * @return TPFirebaseFirestoreQueryBuilder
     */
    fun whereArrayContains(field: String, value: Any): TPFirebaseFirestoreQueryBuilder {
        query = query.whereArrayContains(field, value)
        return this
    }

    /**
     * Filters a collection by documents where the array named `field` contains one or more elements of the array `value`
     *
     * @param field attribute name of the array
     * @param value Array of possible values in the `field` array.
     * @return TPFirebaseFirestoreQueryBuilder
     */
    fun whereArrayContainsAny(field: String, value: List<Any>): TPFirebaseFirestoreQueryBuilder {
        query = query.whereArrayContainsAny(field, value)
        return this
    }

    /**
     * Filters a collection for documents for which the value of the field `field` corresponds to the passed value `value
     *
     * @param field Attribute name of the field
     * @param value The value to find
     * @return TPFirebaseFirestoreQueryBuilder
     */
    fun whereEqualTo(field: String, value: Any): TPFirebaseFirestoreQueryBuilder {
        query = query.whereEqualTo(field, value)
        return this
    }

    /**
     * Filters a collection for documents for which the value of the field `field` explicitly does not match the passed value `value
     *
     * @param field Attribute name of the field.
     * @param value The value that the field `field` must not have.
     * @return TPFirebaseFirestoreQueryBuilder
     */
    fun whereNotEqualTo(field: String, value: Any): TPFirebaseFirestoreQueryBuilder {
        query = query.whereNotEqualTo(field, value)
        return this
    }

    /**
     * Filters a collection by documents where the value of the field `field` is contained in the list `value
     *
     * @param field Attribute name of the field
     * @param value List of possible values that the field `field` may have.
     * @return TPFirebaseFirestoreQueryBuilder
     */
    fun whereIn(field: String, value: List<Any>): TPFirebaseFirestoreQueryBuilder {
        query = query.whereIn(field, value)
        return this
    }

    /**
     * Filters a collection for documents,
     * where the value of the field `field` is explicitly not contained in the list `value
     *
     * @param field Attribute name of the field
     * @param value List of possible values that the field `field` must not have
     * @return TPFirebaseFirestoreQueryBuilder
     */
    fun whereNotIn(field: String, value: List<Any>): TPFirebaseFirestoreQueryBuilder {
        query = query.whereNotIn(field, value)
        return this
    }

    /**
    * Filters a collection for documents,
     * where the value of the field `field` is greater than the value `value`.
     *
     * @param field Attribute name of the field
     * @param value The value to compare
     * @return TPFirebaseFirestoreQueryBuilder
     */
    fun whereGreaterThan(field: String, value: Any): TPFirebaseFirestoreQueryBuilder {
        query = query.whereGreaterThan(field, value)
        return this
    }

    /**
     * Filters a collection by documents,
     * where the value of the field `field` is greater than or equal to the value of `value
     *
     * @param field Attribute name of the field.
     * @param value The value to compare
     * @return TPFirebaseFirestoreQueryBuilder
     */
    fun whereGreaterThanOrEqualTo(field: String, value: Any): TPFirebaseFirestoreQueryBuilder {
        query = query.whereGreaterThanOrEqualTo(field, value)
        return this
    }

    /**
     * Filters a collection for documents,
     * where the value of the field `field` is less than the value `value`.
     *
     * @param field Attribute name of the field
     * @param value The value to compare
     * @return TPFirebaseFirestoreQueryBuilder
     */
    fun whereLessThan(field: String, value: Any): TPFirebaseFirestoreQueryBuilder {
        query = query.whereLessThan(field, value)
        return this
    }

    /**
     * Filters a collection for documents,
     * where the value of the field `field` is less than or equal to the value of `value
     *
     * @param field Attribute name of the field.
     * @param value The value to compare
     * @return TPFirebaseFirestoreQueryBuilder
     */
    fun whereLessThanOrEqualTo(field: String, value: Any): TPFirebaseFirestoreQueryBuilder {
        query = query.whereLessThanOrEqualTo(field, value)
        return this
    }

    /**
     * Sorts the result set by the field 'field'.
     * (Default: Ascending sort)
     *
     * @param field Attribute name of the field
     * @param descending Defines whether the result set should be sorted in descending order
     * @return TPFirebaseFirestoreQueryBuilder
     */
    fun orderBy(field: String, descending: Boolean = false): TPFirebaseFirestoreQueryBuilder {
        query = query.orderBy(
            field,
            if (descending)
                Query.Direction.DESCENDING
            else
                Query.Direction.ASCENDING
        )
        return this
    }

    /**
     * Limits the result set in its scope.
     *
     * @param limit Maximum number of elements in the result set.
     * @return TPFirebaseFirestoreQueryBuilder
     */
    fun limit(limit: Int): TPFirebaseFirestoreQueryBuilder {
        query = query.limit(limit.toLong())
        return this
    }

    internal fun getCollectionName(): String {
        return collectionName
    }

    internal fun getQuery(): Query {
        return query
    }
}
