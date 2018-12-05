package com.thorgil.gambler.dataproviders

/**
 * NOTE: It could be that the data provider expects a Single Sing On generated
 * JSON Web Token for authorization purposes along with each request. At present
 * we are parking these concerns.
 *
 * The point is we expect some authorization mechanism and hope that using an
 * Authorization header using the Bearer scheme would be a possibility.
 */
interface DataProviderInterface {

    /**
     * Used to get the user's balance.
     *
     * Should be used before a debit or credit to ascertain that it is possible and also
     * from time to time while a user is logged in to a web app (their credit balance
     * could be in flux due to activity on other systems).
     *
     * @param cardNumber
     * @return the user, may return null if the user was not found
     * @throws RuntimeException if not authorized
     */
    fun getUserDetails(cardNumber: String): DataProviderUser?

    /**
     * Debit the specified user's points.
     *
     * ASSUMPTION: A user's point balance may not go negative.
     * ASSUMPTION: There is not limit to the number of points that may be deduced in one go (appart what in
     * inherent in the datatype).
     *
     * @param cardNumber the user's card number (this field may be unnecessary with JWT Bearer Tokens)
     * @param points must be a positive value
     * @return the user's details after the debit operation
     * @throws RuntimeException if the operation failed for a number of possible reasons
     * @throws RuntimeException if not authorized
     */
    fun debit(cardNumber: String, points: Int): DataProviderUser?

    /**
     * Credit the specified user's points.
     *
     * @param cardNumber the user's card number (this field may be unnecessary with JWT Bearer Tokens)
     * @param points must be a positive value
     * @return the user's details after the credit operation
     * @throws RuntimeException if the operation failed for a number of possible reasons
     * @throws RuntimeException if not authorized
     */
    fun credit(cardNumber: String, points: Int): DataProviderUser?
}