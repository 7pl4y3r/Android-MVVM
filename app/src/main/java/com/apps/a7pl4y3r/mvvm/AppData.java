package com.apps.a7pl4y3r.mvvm;

public abstract class AppData {

    public static final String databaseName = "notes.db";
    public static final String tableName = "notes_table";

    public static final String EXTRA_ID = "com.apps.a7pl4y3r.mvvm.EXTRA_ID";
    public static final String EXTRA_TITLE = "com.apps.a7pl4y3r.mvvm.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION = "com.apps.a7pl4y3r.mvvm.EXTRA_DESCRIPTION";
    public static final String EXTRA_PRIORITY = "com.apps.a7pl4y3r.mvvm.EXTRA_PRIORITY";

    public static final int Add_Note_Request = 1;
    public static final int EDIT_NOTE_REQUEST = 2;

}
