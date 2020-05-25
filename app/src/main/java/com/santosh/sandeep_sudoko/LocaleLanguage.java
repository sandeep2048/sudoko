package com.santosh.sandeep_sudoko;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;

import java.util.Locale;

public class LocaleLanguage {
    public static final int DEFAULTLAN = 0;
    public static final int ENGLISH = 1;
    public static final int ARABIC = 2;
    public static final int BENGALI = 3;
    public static final int CHINASIMPLE = 4;
    public static final int CHINACOMPLEX = 5;
    public static final int HINDI = 6;
    public static final int URDU = 7;
    public static final int ETHOPIA = 8;
    public static final int GUJARATI = 9;
    public static final int GUMUKI = 10;
    public static final int KANNADA = 11;
    public static final int KHMER = 12;
    public static final int LAO = 13;
    public static final int LIMBU = 14;
    public static final int MALAYALAM = 15;
    public static final int MANGOLIAN = 16;
    public static final int MYANMAR = 17;
    String[] lang;

    LocaleLanguage(Context context) {
        lang = new String[18];
        lang[DEFAULTLAN] = context.getString(R.string.language_option_default);
        lang[ENGLISH] = context.getString(R.string.language_name_english);
        lang[ARABIC] = context.getString(R.string.language_name_Arabic);
        lang[BENGALI] = context.getString(R.string.language_name_Bengali);
        lang[CHINASIMPLE] = context.getString(R.string.language_name_spanish);
        lang[CHINACOMPLEX] = context.getString(R.string.language_name_french);
        lang[HINDI] = context.getString(R.string.language_name_hindi);
        lang[URDU] = context.getString(R.string.language_name_italian);
        lang[ETHOPIA] = context.getString(R.string.language_name_japanese);
        lang[GUJARATI] = context.getString(R.string.language_name_marathi);
        lang[GUMUKI] = context.getString(R.string.language_name_dutch);
        lang[KANNADA] = context.getString(R.string.language_name_portuguese_brazil);
        lang[KHMER] = context.getString(R.string.language_name_turkish);
        lang[LAO] = context.getString(R.string.language_name_russian);
        lang[LIMBU] = context.getString(R.string.language_name_tamil);
        lang[MALAYALAM] = context.getString(R.string.language_name_telugu);
        lang[MANGOLIAN] = context.getString(R.string.language_name_chinese);
        lang[MYANMAR] = context.getString(R.string.language_name_danish);

    }

    public static void updateLanguage(int i, Context context) {
        Configuration config = new Configuration();
        switch (i) {
            case ENGLISH:
                config.locale = new Locale("en");
                break;

            case ARABIC:
                config.locale = new Locale("ar");
                break;
            case BENGALI:
                config.locale = new Locale("bn");
                break;
            case CHINASIMPLE:
                config.locale = new Locale("zh");
                break;
            case CHINACOMPLEX:
                config.locale = new Locale("zh", "CN");
                break;
            case HINDI:
                config.locale = new Locale("hi");
                break;
            case URDU:
                config.locale = new Locale("ur");
                break;
            case ETHOPIA:
                config.locale = new Locale("so", "ET");
                break;
            case GUJARATI:
                config.locale = new Locale("gu", "IN");
                break;
            case GUMUKI:
                config.locale = new Locale("b", "guz");
                break;
            case KANNADA:
                config.locale = new Locale("kn");

                break;
            case KHMER:
                config.locale = new Locale("guz");
                break;
            case LAO:
                config.locale = new Locale("lo");
                break;
            case LIMBU:
                config.locale = new Locale("li");
                break;
            case MALAYALAM:
                config.locale = new Locale("ml", "IN");
                break;
            case MANGOLIAN:
                config.locale = new Locale("mn");
                break;
            case MYANMAR:
                config.locale = new Locale("my", "MM");
                break;
            case DEFAULTLAN:
            default:
                config.locale = defaultLanguage();
                break;
        }
        if (context instanceof Activity) {
            ((Activity) context).getApplication()
                    .getResources()
                    .updateConfiguration(config, null);
        }
        context.getResources()
                .updateConfiguration(config, null);

    }

    private static Locale defaultLanguage() {

        Locale locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            locale = Resources.getSystem()
                    .getConfiguration()
                    .getLocales()
                    .getFirstMatch(new String[]{"en", "zh", "fr", "de", "ja", "es", "da", "it", "tr", "ru", "nl", "pt-rBR", "ta", "hi", "mr", "bn", "te"});//No I18N
        } else {
            //noinspection deprecation
            locale = Resources.getSystem()
                    .getConfiguration().locale;
        }

        return locale;

    }
}

