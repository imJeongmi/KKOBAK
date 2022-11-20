package com.a104.kkobak.ui.util;

import android.os.AsyncTask;

import com.a104.kkobak.data.room.dao.AccessTokenDao;
import com.a104.kkobak.data.room.entity.AccessToken;

import java.util.List;

public class DatabaseAccess {
    public static class getAccessTokenAsyncTask extends AsyncTask<Void, Void, AccessToken> {
        private final AccessTokenDao accessTokenDao;

        public getAccessTokenAsyncTask(AccessTokenDao accessTokenDao) {
            this.accessTokenDao = accessTokenDao;
        }

        @Override
        protected AccessToken doInBackground(Void... voids) {
            List<AccessToken> tokens = accessTokenDao.getAll();
            if (tokens == null || tokens.size() == 0)
                return (null);
            else
                return (tokens.get(0));
        }
    }

    public static class LogoutAsyncTask extends AsyncTask<Void, Void, Void> {
        private final AccessTokenDao accessTokenDao;

        public LogoutAsyncTask(AccessTokenDao accessTokenDao) {
            this.accessTokenDao = accessTokenDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            accessTokenDao.deleteAll();

            return null;
        }
    }
}
