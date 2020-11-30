package com.dreamdiary.Database;

import androidx.annotation.NonNull;

public interface IDataBaseController {


    /**
     *
     * @param TITLE refer to the title of the dream, to insert in the db
     * @param DATE refer to the date of the dream was created, to insert in the db
     * @param TEXT refer to the text content in the dream, to insert in the db
     * @return a boolean value that indicate if the dream insert successful or not
     *
     * */
    boolean insertDream(@NonNull final String TITLE,
                        @NonNull final String DATE,
                        @NonNull final String TEXT);

    /**
     *
     * @param ID refer to the ID of the dream, necessary for deleting the dream
     * @return a boolean value that indicate if the dream deleting successful or not
     *
     * */
    boolean deleteDreamByID(@NonNull final String ID);


}
