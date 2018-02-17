package com.possoajudar.app.infrastructure;

import com.possoajudar.app.BuildConfig;

/**
 * Created by renato on 13/02/2018.
 */

public class Constants {
    public class Headers {
        public static final String PLATFORM = "platform";
        public static final String PLATFORM_VERSION = "platformVersion";
        public static final String TIMESTAMP = "timestamp";
        public static final String VERSION_NAME = "versionName";

        //public static final String URL = BuildConfig.ENDPOINT;
        public static final String URL = "http://api.themoviedb.org/3/";
        public static final String URL_CEP = "https://viacep.com.br/ws/22441-000/json/";

        public static  final String END_POINT_APONTAMENTO_WEEK = "/apontamento/week";
        public static  final String END_POINT_APONTAMENTO_WEEKEND = "/apontamento/weekend";
        public static  final String END_POINT_APONTAMENTO_MONTH = "/apontamento/month";
        public static  final String END_POINT_APONTAMENTO_YEAR = "/apontamento/year";

        public static final String API_KEY = "50a888ee9e4206496cb6ef949d8c7a44";
        public static final String URL_IMG = "http://image.tmdb.org/t/p/";
        public static final String TAMANHO_PADRAO = "w185/";
        public static final String URL_IMG_COMPLETO = URL_IMG + TAMANHO_PADRAO;

    }
}
