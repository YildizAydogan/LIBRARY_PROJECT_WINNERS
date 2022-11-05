package com.winners.libraryproject.payload.messages;

public class ErrorMessage {

    public final static String ROLE_NOT_FOUND_MESSAGE="Role with id %s not found";
    public final static String RESOURCE_NOT_FOUND_MESSAGE="Resource with id %d not found";
    public final static String BOOK_NOT_FOUND_MESSAGE="Book with id %d not found";
    public final static String BOOK_NOT_AVAILABLE_MESSAGE="Book with id %d cant loanable";
    public final static String LOAN_NOT_FOUND_MSG="Loan with id %d not found";

    public final static String AUTHOR_NOT_FOUND_MESSAGE="Author with id %d not found";

    public final static String PUBLISHER_NOT_FOUND_MESSAGE="Publisher with id %d not found";

    public final static String CATEGORY_NOT_FOUND_MESSAGE="Category with id %d not found";

    public final static String USER_NOT_FOUND_MESSAGE="User with id %d not found";
    public final static String USER_USED_BY_LOAN_MESSAGE = "User could not be deleted. User loaned a book";
    public final static String PASSWORD_NOT_MATCHED="You password are not matched";

    public final static String NOT_PERMITTED_METHOD_MESSAGE="You don't have any permission to change this value";
    public final static String EMAIL_ALREADY_EXIST="this email already exist";

    public final static String EMAIL_ALREADY_EXIST1="This email already exists";

    public final static String BOOK_LOANED_OUT = "Book already loaned";


}