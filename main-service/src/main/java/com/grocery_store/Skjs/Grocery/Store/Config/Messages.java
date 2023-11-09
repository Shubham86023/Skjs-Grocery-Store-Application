package com.grocery_store.Skjs.Grocery.Store.Config;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public final class Messages {

    // Messages for Login controller
    public static final String WRONG_CREDENTIALS = "You Entered Wrong Login Credentials.";
    public static final String PASSWORD_UPDATED = "Your Password Updated Successfully";
    public static final String NO_ACCOUNT_AVL = "No Account Registered with this Credentials.";
    public static final String BOTH_PASS_NOT_MATCH = "Oops.. Your Confirm Password Not Matches With Original Passowrd.";

    // Messages for Signup controller
    public static final String ACCOUNT_CREATED = "Account Created Successfully...!! Now Please Login.";
    public static final String ERROR_404 = "Oops.. Something Went Wrong.";
    public static final String UNQ_EMAIL_MOBILE = "Please Enter Unique Email & Moblie Number.";
    public static final String ACCOUNT_UPDATED = "Your Account Details Updated Successfully.";

    // Messages for Bwelcome controller
    public static final String BSNSS_DTL_UPDATED = "Your Business Details updated Successfully.";

}
