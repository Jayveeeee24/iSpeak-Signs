package com.artemis.ispeaksigns;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "iSpeakDB";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {

        //DATABASE TABLE FOR USER TABLE
        DB.execSQL("create Table IF NOT EXISTS UserTable(userID INTEGER primary key ," +
                " userName TEXT , isOldUser INTEGER , dateToday TEXT , currentStreak INTEGER," +
                "longestStreak INTEGER, itemNameWOTD INTEGER , selectedLanguage TEXT, avatarName TEXT, isBadge INTEGER)");

        ContentValues userValue = new ContentValues();
        userValue.put("userID", 201810336);
        userValue.put("userName", "Juan");
        userValue.put("isOldUser", 0);
        userValue.put("dateToday", "Mon Apr 02 2022");
        userValue.put("currentStreak", 0);
        userValue.put("longestStreak", 0);
        userValue.put("itemNameWOTD", 0);
        userValue.put("selectedLanguage", "tl");
        userValue.put("avatarName", "avatar1");
        userValue.put("isBadge", 0);
        DB.insert("UserTable", null, userValue);

        //DATABASE TABLE FOR CATEGORY TABLE
        DB.execSQL("create Table IF NOT EXISTS CategoryTable(categoryName TEXT primary key," +
                " categoryColor TEXT, categoryTotalItems INTEGER," +
                " categoryType TEXT, imageURL TEXT," +
                " categoryProgress INTEGER)");

        String[] categoryName = new String[]{
                "Araw ng Linggo", "Buwan", "Lugar", "Emosyon", "Alpabeto",
                "Numero", "Hayop", "Hugis", "Kulay", "Prutas",
                "Gulay", "Parte ng Katawan", "Transportasyon", "Kasarian", "Miyembro ng Pamilya",
                "Pagbati", "Pang-Emergency", "Pangkomunikasyon", "Ekspresyon ng Oras", "Ekspresyon ng Pagmamahal"};

        String[] bgColors = new String[]{
                        "outrageous_orange", "auburn", "bright_navy_blue", "jungle_green", "plump_purple",
                        "tulip", "japanese_indigo", "silver_lake_blue", "pink", "blue_cola",
                        "golden_puppy", "cornflower_blue", "flame", "apple", "may_green",
                        "veronese_green", "violet_blue", "grape", "steel_teal", "blue_surf"};
        int[] itemCount = new int[]{7, 12, 7, 7, 26, 11, 7, 5, 7, 6, 11, 8, 6, 2, 7, 5, 7, 10, 8, 6};

        String[] imageURL = new String[]{
                "ic_araw_ng_linggo", "ic_buwan", "ic_lugar", "ic_emosyon", "ic_alpabeto",
                "ic_numero", "ic_hayop", "ic_hugis", "ic_kulay", "ic_prutas",
                "ic_gulay", "ic_parte_ng_katawan", "ic_sasakyan", "ic_kasarian", "ic_miyembro_ng_pamilya",
                "ic_pagbati", "ic_pang_emergency", "ic_pangkomunikasyon", "ic_ekspresyon_ng_oras", "ic_ekspresyon_ng_pagmamahal"};

        for (int i = 0; i<categoryName.length; i++){
            ContentValues values = new ContentValues();
            values.put("categoryName", categoryName[i]);
            values.put("categoryColor", bgColors[i]);
            values.put("categoryTotalItems", itemCount[i]);
            if (i<15){
                values.put("categoryType", "Salita");
            }else{
                values.put("categoryType", "Parirala");
            }
            values.put("imageURL", imageURL[i]);
            values.put("categoryProgress", 0);
            DB.insert("CategoryTable", null, values);
        }

        //DATABASE FOR ITEM TABLE
        DB.execSQL("create Table IF NOT EXISTS ItemTable(itemName TEXT primary key," +
                " itemCategory TEXT, itemType TEXT, isLearned INTEGER, partsOfSpeech TEXT, imagesNo INTEGER, youtubeId TEXT, howTo TEXT)");

        String[] itemName = new String[]{
                //ARAW NG LINGGO
                "Lunes", "Martes", "Miyerkules", "Huwebes", "Biyernes", "Sabado", "Linggo",
                //MGA BUWAN
                "Enero", "Pebrero", "Marso", "Abril", "Mayo", "Hunyo", "Hulyo", "Agosto", "Setyembre", "Oktubre", "Nobyembre", "Disyembre",
                //LUGAR
                "Bahay", "Botika", "Ospital",  "Paaralan", "Palengke", "Parke", "Simbahan",
                //EMOSYON
                "Galit", "Gulat", "Hiya", "Lungkot", "Saya", "Takot", "Tuwa",
                //ALPABETO
                "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z",
                //NUMERO
                "Sero", "Isa", "Dalawa", "Tatlo", "Apat", "Lima", "Anim", "Pito", "Walo", "Siyam", "Sampu",
                //HAYOP
                "Aso", "Ahas", "Ibon", "Isda", "Palaka", "Pusa", "Unggoy",
                //HUGIS
                "Bilog", "Bituin", "Parihaba", "Parisukat", "Tatsulok",
                //KULAY
                "Berde", "Bughaw", "Dilaw", "Itim", "Lila", "Pula", "Puti",
                //PRUTAS
                "Buko", "Mansanas", "Mangga", "Pakwan", "Pinya", "Saging",
                //GULAY
                "Bawang", "Kalabasa", "Kamatis", "Patatas", "Petsay", "Pipino", "Repolyo", "Sibuyas", "Sili", "Sitaw", "Talong",
                //PARTE NG KATAWAN
                "Bibig", "Ilong", "Kamay", "Leeg", "Mata", "Paa", "Tainga", "Ulo",
                //TRANSPORTASYON
                "Bangka", "Bisikleta", "Bus", "Dyip", "Eroplano", "Motorsiklo",
                //KASARIAN
                "Babae", "Lalaki",
                //MIYEMBRO NG PAMILYA
                "Nanay", "Tatay", "Ate", "Kuya", "Lolo", "Lola", "Sanggol",

                //PAGBATI
                "Kamusta ka?", "Magandang umaga sa iyo", "Magandang hapon sa iyo", "Magandang gabi sa iyo", "Paalam na sa’yo",
                //PANG-EMERGENCY
                "Tulungan nyo ako!", "Nananakawan ako!", "Ako ay nawawala!", "Paki bilisan po", "Anong lugar ito?", "Maaari bang humiram ng telepono?", "Paki tigil po",
                //PANGKOMUNIKASYON
                "Ano ang pangalan mo?", "Patawarin mo ako", "Salamat sa iyo!", "Saan ka nakatira?", "Ingat ka sa iyong patutunguhan!", "Magandang araw sa’yo!", "Kain tayo!", "Nauunawaan mo ba ako?", "Nasaan ang banyo?", "Nasaan ang kusina?",
                //EKSPRESYON NG ORAS
                "Anong oras na?", "Pwede mo bang sabihin ang oras?", "Alam mo ba kung anong oras na?", "Alas sais na ng umaga", "Alas dose na ng tanghali", "Alas otso na ng gabi", "Magkita tayo mamaya", "Magkita tayo bukas",
                //EKSPRESYON NG PAGMAMAHAL
                "Nandito lang ako para sa’yo", "Ikaw ay isang kaakit-akit na babae", "Siya ay may may magandang mata", "May paghanga ako sa'yo", "Gusto kitang yakapin", "Gusto kita"
        };

        String[] howTo = new String[]{
                //Araw ng Linggo
                "isenyas ang letrang ‘M’ paikot sa kaliwang direksyon ", "isenyas ang letrang ‘T’ paikot sa kanang direksyon ", "isenyas ang letrang ‘W’ paikot sa kaliwang direksyon ", "isenyas ang letrang ‘T’ sunod ay ipalit ang senyas ng letrang ‘H’ ", "isenyas ang letrang ‘F’ paikot sa kaliwang direksyon ", "isenyas ang letrang ‘S’ paikot sa kaliwang direksyon", "buksan ang magkabilang palad at isenyas paikot sa kaliwang direksyon ",
                //Buwan
                "isenyas ang letrang ‘D’ sa kaliwang kamay at isenyas ang letrang ‘J’ sa kanang kamay mula sa likod ng kaliwang kamay pababa nito", "isenyas ang letrang D sa kaliwang kamay at isenyas ang letrang ‘F’ sa kanang kamay mula sa likod ng kaliwang kamay pababa nito", "isenyas ang letrang ‘D’ sa kaliwang kamay at isenyas ang letrang ‘M’ sa kanang kamay mula sa likod ng kaliwang kamay pababa nito", "isenyas ang letrang ‘D’ sa kaliwang kamay at isenyas ang letrang ‘A’ pagkatapos ay letrang ‘L’ sa kanang kamay mula sa likod pababa", "isenyas ang letrang ‘D’ sa kaliwang kamay at isenyas ang letrang ‘M’ pagkatapos ay letrang ‘Y’ naman sa kanang kamay mula sa likod pababa", "isenyas ang letrang ‘D’ sa kaliwang kamay at isenyas ang letrang ‘M’ pagkatapos ay letrang ‘Y’ naman sa kanang kamay mula sa likod pababa", "isenyas ang letrang ‘D’ sa kaliwang kamay at isenyas ang letrang ‘J’ pagkatapos ay letrang ‘Y’ sa kanang kamay mula sa likod pababa", "isenyas ang letrang ‘D’ sa kaliwang kamay at isenyas ang letrang ‘A’ pagkatapos ay letrang ‘V’ sa kanang kamay mula sa likod pababa", "isenyas ang letrang ‘D’ sa kaliwang kamay at isenyas ang letrang ‘S’ sa kanang kamay mula sa likod ng kaliwang kamay pababa nito", "isenyas ang letrang ‘D’ sa kaliwang kamay at isenyas ang letrang ‘O’ sa kanang kamay mula sa likod ng kaliwang kamay pababa nito", "isenyas ang letrang ‘D’ sa kaliwang kamay at isenyas ang letrang ‘N’ sa kanang kamay mula sa likod ng kaliwang kamay pababa nito", "isenyas ang letrang ‘D’ magkabilang kamay pagkatapos ay isenyas ang kanang kamay mula sa likod pababa ng kaliwang kamay",
                //LUGAR
                "ibukas ang palad at ipagdikit ang dulong daliri sa isat isa na parang isang bahay", "gamit ang gitnang daliri sa kanang kamay,  ipatong ito sa gitnang bahago ng kaliwang palad at saka marahang iikot pakaliwa. Ngayon ay sabay na iikom at pagtapatin ang kanan at kaliwang kamay pagkatapos ay iwisik ng dalawang beses", "isenyas ang letrang ‘U’ sa kanang kamay, humagod ng patayong linya sa kaliwang balikat pababa sa braso at saka humagod ng pahalang na linya mula sa kaliwang direksyon papunta sa kanang direksyon ng balikat upang makabuo ng hugis krus",  "buksan ang magkabilang palad, patagilidin ang kaliwa at saka ipalakpak ang kanang kamay sa gitnang bahagi ng kaliwang palad", "isenyas sa magkabilaang kamay ang letrang ‘M’, ngunit sa puntong ito nakaharap at nakabuka ang tatlong mga daliri na gumagalaw ng pauna at palikod ng paulit-ulit", "ilapat ang kaliwang likod ng palad at ipatong ang nakaikom na kanang kamay at saka bumuo ng pabilog na hugis sa direksyong pakaliwa habang nakabuka ang mga daliri sa kanang kamay", "isenyas ang letrang ‘C’ sa kanang kamay at ipatong sa kaliwang likod ng palad at saka tapikin ng dalawang beses",
                //EMOSYON
                "isenyas ang letrang C sa kanang kamay ng magkakahiwalay ang mga daliri pagkapos ay itapat ito sa mukha nang may kaunting distansya at saka humila pababa", "pag dikitin ang dalawang kamao sa magkabilaang gilid at pagkatapos nito ay isenyas ang letrang ‘L’ sa magkabilaang kamay ng palayo sa isa’t isa", "gamit ang kabilaang likod ng palad, ikiskis ito sa pisngi nang dahan-dahan", "isenyas ang letrang C sa magkabilang kamay, idikit ito sa gilid ng labi at saka paghiwalayin nang pakurba pababa", "buksan ang magkabilang palad, itapat ito sa dibdib nang may kaunting distansya at saka ikutin nang sabay", "ibuka ang magkabilaang palad sa puntong ito ay nasa may tapat na ng dibdib", "isenyas ang letrang ‘G’ sa magkabilaang kamay at saka ito igalaw pasalubong sa isa’t isa ng paulit- ulit",
                //ALPABETO
                "iikom ang mga daliri sa kamay nang nasa gilid ang hinlalaki", "isenyas ang numerong apat at saka pagdikitin ang mga daliri", "ihugis ang kalahating bilog gamit ang mga daliri sa kamay", "bumuo ng hugis bilog sa isang kamay at saka itaas ang hintuturo", "itupi ang mga daliri sa kamay", "bumuo ng hugis bilog gamit ang hinlalaki at hintuturong daliri at saka itaas ang mga natitira pang mga daliri", "iikom ang mga daliri sa kamay, pagkatapos ay sabay na itaas ang hintuturo at hinlalaki. Ituro ito sa pahalang na direksyon", "iikom ang mga daliri sa kamay, pagkatapos ay sabay na itaas ang gitnang daliri at hintuturo. Ituro ito sa pahalang na direksyon", "iikom ang mga daliri sa kamay, pagkatapos ay itaas ang hinliliit", "iikom ang mga daliri sa kamay, pagkatapos ay itaas ang hinliliit at saka gumuhit sa hangin ng letrang J", "iikom ang mga daliri sa kamay, pagkatapos ay sabay na itaas at paghiwalayin ang gitnang daliri at hintuturo at saka isingit sa gitna ang hinlalaki", "iikom ang mga daliri sa kamay, pagkatapos ay sabay na itaas ang hintuturo at hinlalaki", "iikom ang mga daliri sa kamay, pagkatapos ay isingit ang hinlalaki sa gitna ng hinliliit at palasingsingan", "iikom ang mga daliri sa kamay, pagkatapos ay isingit ang hinlalaki sa gitna ng palasingsingan at gitnang daliri", "bumuo ng hugis bilog sa isang kamay", "iikom ang mga daliri sa kamay, pagkatapos ay sabay na itaas at paghiwalayin ang gitnang daliri at hintuturo at saka isingit sa gitna ang hinlalaki. Ituro ito sa direksyon pababa", "iikom ang mga daliri sa kamay, pagkatapos ay sabay na itaas ang hintuturo at hinlalaki. Ituro ito sa direksyon pababa", "iikom ang mga daliri sa kamay, pagkatapos ay sabay na itaas ang gitnang daliri at hintuturo. Ipatong ang gitnang daliri sa likod ng hintuturo", "iikom ang mga daliri sa kamay nang nasa gitna ang hinlalaki", "iikom ang mga daliri sa kamay, pagkatapos ay sabay na itaas ang hintuturo at hinlalaki. Ipatong sa ibabaw ng hinlalaki ang hintuturo", "iikom ang mga daliri sa kamay, pagkatapos ay sabay na itaas ang gitnang daliri at hintuturo, saka pagdikitin", "iikom ang mga daliri sa kamay, pagkatapos ay sabay na itaas ang gitnang daliri at hintuturo, saka paghiwalayin", "pagdikitin ang hinliliit at hinlalaki. Ang mga natirang daliri ay itaas at saka paghiwalayin", "iikom ang mga daliri sa kamay, pagkatapos ay itaas ang hintuturo at saka itupi sa kalahati", "iikom ang mga daliri sa kamay, pagkatapos ay sabay na itaas ang hinliliit at hinlalaki", "iikom ang mga daliri sa kamay, pagkatapos ay itaas ang hintuturo, saka gumuhit sa hangin ng letrang Z",
                //NUMERO
                "isenyas ng pa-letrang ‘O’ ang isang kamay", "isenyas ang kamay ng nakaturo ang daliri sa itaas na direksyon", "mag senyas ang isang kamay ng pa letrang ‘V’", "isenyas ang isang kamay ng paletrang ‘V’ ngunit sa puntong ito ang hinlalaking daliri ay nakabuka rin", "isenyas ng letrang ‘B’ ang isang kamay na magkakahiwa-hiwalay ang bawat daliri", "buksang at iharap ang palad sa unahan na magkakahiwa-hiwalay ang bawat daliri", "sa nakasaradong kamao, ibuka o itayo lamang ang tatlong daliri na nasa gitna habang ang dalawa sa mag kabilang dulong daliri ay naka tiklop", "isenyas  na letrang ‘V’ ang isang kamay subalit sa puntong ito ang hinliliit na daliri ay isa rin sa mga nakabuka o nakatayo", "sa isang nakabukang palad, itiklop ang gitna at hinlalaking daliri ", "isenyas ng letrang ‘F’ ang isang kamay nang magkakahiwalay ang mga daliri", "isenyas ang kamay ng letrang ‘A’ na ang hinlalaking daliri ang nasa ibabaw at nakaturo sa itaas, sabay igalaw ng kaliwa at kanan ng paulit-ulit",
                //HAYOP
                "itapik ang kanang kamay sa bewang at saka ito isenyas o igalaw ng papitik", "pag dikit-dikitin ang mga daliri sa isang kamay ng nakakurba ng kapiraso at saka ito igagalaw ng patalon talon papunta paunahan o kabilang direksyon", "isenyas ang letrang ‘Q’ na nakatagilid at malapit sa bibig, ngunit sa puntong ito ang hintuturong daliri ay siyang nakataas at ang gitnang daliri ang nakadikit sa hinlalaki habang ang iba naman ay nakatiklop lamang. Kasunod nito, ay igagalaw ng pababa at taas ang hintuturong daliri ", "isenyas ang letrang ‘F’ na nakatalikod at nakatagilid, pagkatapos ay igalaw ang dulo ng daliri na parang buntot ng isda", "sa nakasaradong kanang kamao, ibuka o itayo ang hinliliit, hintuturo, at hinlalaking daliri at ipatong ito sa nakasahod na kaliwang palad. Kasunod nito, igalaw ng pataas ang kanang kamay habang bumubuka ang mga nakatiklop na daliri nito", "isenyas ang letrang ‘F’ sa magkabilang kamay ngunit ang hintuturo at hinlalaki ay parang may hinihila mula sa ibabaw ng labi", "isenyas ang magkabilang daliri na parang nakatupi na nakatapat sa noo at ang kaliwang kamay ay nakatapat sa dibdib pagkatapos ay igalaw ng parang kumakamot o may kinakamot",
                //HUGIS
                "isenyas ang letrang ‘C’ at igalaw paikot sa kaliwa  ", "gamit ang mga hintuturo na nakaturo sa itaas na direksyon, igalaw ang mga kamay sa paitaas at baba na hindi magkasabay", "isenyas sa dalawang kamay ang letrang ‘R’ at bumuo ng isang Parihaba gamit ang dalawang kamay ", "isenyas sa dalawang kamay ang letrang ‘S’ at bumuo ng isang parisukat gamit ang dalawang kamay ", "isenyas sa dalawang kamay ang letrang ‘T’ at bumuo ng isang tatsulok gamit ang dalawang kamay ",
                //KULAY
                "isenyas ang letrang ‘G’ at igalaw ang hintuturo nang papasok at palabas ng dalawang beses o higit", "isenyas ang letrang ‘B’ at igalaw ang hintuturo nang papasok at palabas ng dalawang beses o higit", "isenyas ang letrang ‘Y’ at igalaw ang hintuturo nang papasok at palabas ng dalawang beses o higit", "isenyas ang letrang ‘D’ sa ibabaw ng ulo, igalaw ang hintuturo palikod", "isenyas ang letrang ‘V’ at igalaw ang kamay nang papasok at plabas ng dalawang beses o higit", "gamit ang hintuturo igalaw ang kamay mula sa bustas ng ilong pababa ng dalwang beses o higit pa", "isenyas ang parang may hinuhugot mula at palayo sa dibdib",
                //PRUTAS
                "isenyas ang dalawang kamay na letrang ‘C’ at ipwesto ito sa di magkapantay na posisyon  at igalaw ito paharap o pasulong", "gamit ang senyas na letrang ‘A’, ang hinlalaking daliri ay siyang nakadikit sa pisngi at gagalaw ng pababa", "isenyas ang kanang kamay na nakabaliktad na letrang ‘C’ at ang isa naman ay ibuka ang palad at igagalaw na para bang naghihiwa mula s a isang kamay", "isenyas ang kaliwang kamay na nakasaradong kamao at ang isa o ang kanang kamay naman ay pumipitik sa kaliwang kamao", "isenyas ang letrang ‘F’ at ang butas o bilog  sa isinenyas ay idikit una sa kanang mata papunta sa kabilang mata. Kasunod nito ay isensyas naman ulit ang kanang kamay sa letrang ‘V’ at idikit sa noo", "pagdikit-dikitin ang mga daliri ng isang kamay na parang isang itsurang bulaklak at ang isa naman ay isesenyas na para bang may binabalatan sa kabiyak na kamay",
                //GULAY
                "isenyas ang kaliwang kamay ng nakasahod ang palad at ang kabila naman ay sesenyas na para bagang may hawak na butil at ilalagay sa nakasahod na palad. Pagkatapos, sa parehong kamay (kanan), mag popostura ito ng nakasaradong kamao at gagalaw ng para bang nagdidikdik sa kaliwang palad", "isenyas ng nakakurba ang dalawang kamay o palad at mula sa ulo igagalaw ito pababa  papuntang baba hanggang sa magdikit ang parte ng kamay na malapit sa pulso", "pagdikit-dikitin ang mga dulo ng daliri sa kaliwang kamay na mag mumukha itong isang bulaklak at ang parte nito ang nakaharap sa kanang bahagi. Isenyas nang letrang ‘D’ ang kanang kamay at ang hintuturo ay gagalaw ng padaplis o didikit sa nguso ng kaliwang kamay", "isenyas ng nakasaradong kamao ang kaliwang kamay na nakaharap kaunti sa kanang bahagi. Ang kabilang kamay naman ay nakasenyas ng letrang ‘V’ ngunit ang hintuturo at ang  gitnang daliri ay naka yuko at tumatapik sa ibabaw ng kaliwang nakasenyas na kamay", "Isenyas ng pa-letrang ‘S’ ang magkabilaang kamay at pagtapatin ito sa isa’t isa. Kasunod nito, buksan ng sabay ang palad habang magkatapat at magkadikit ang malapit sa pulsong parte ng kamay", "Isenyas ng pa-letrang ‘C’ ang kanang kamay, ngunit sa puntong ito ang mga daliri ay magkakahiwalay sa isa’t isa. Kasunod nito, ilagay ang naka bukang parte una ay sa kanang mata papunta sa kabila", "ikurba ang dalawang kamay nang nakadapa at igalaw ito ng nag papatong sa isa’t isa nang paulit-ulit at hindi umaalis sa pwesto", "pagdikit-dikitin ang mga dulong daliri sa kanang kamay na magmumukha itong isang bulaklak. Idikit ang parte nito sa gilid ng mga mata habang ginagalaw o pinipihit ng pauna at palikod habang ang mukha ay may ekspresyon na parang umiiyak", "Sumenyas ng letrang ‘B’ at itapat ito sa bibig ng padapa habang gumagalaw ng pataas at baba ng ilang beses", "isenyas ng letrang ‘B’ ang kaliwang kamay ng patagilid na nakaharap ang likod na bahagi ng palad sa unahan. Ang kabilang kamay naman ay isenyas ng ‘okay’ na nakabaligtad at ang hinlalaking daliri nito ang didikit at gagalaw mula kaliwa papunta sa kanan o dulong parte ng daliri sa kaliwang kamay. Kasunod nito mula sa kaparehong kamay ay isesenyas ito ng letrang ‘F’ na nakayuko at ang hintuturo ay gagalaw na parang sipit na papunta sa kaliwang parting bahagi naman", "isenyas ng nakaturo ang kaliwang kamay ngunit ito ay naka tagilid at nakatutok patungo sa kanang direksyon. Ang kabilang kamay naman ay isenyas ang mga daliri na parang may babalutin o hawak na bola, kasabay nito sa kaparehong kamay, babalutin nito ang kaliwang hintuturong daliri at gagalaw na para bang may kinukuha o hinihila palayo sa naturang daliri",
                //PARTE NG KATAWAN
                "isenyas ang letrang ‘M’ at idikit ang dulo sa bibig pagkatapos ay igalaw ng paikot", "gamit ang hintuturo, ituro lamang ang ilong", "isenyas ang letrang ‘H’ sa magkabilang kamay at igalaw ang kanang kamay paikot sa kaliwang kamay pagkatapos ay itigil sa taas ng isang kamay", "isenyas ang letrang ‘N’ at ituro mula sa itaas ng leeg papunta sa baba nito", "Isenyas ang letrang ‘E’ sa isang kamay at saka ito itapat sa ibabang bahagi ng mata papunta sa kabilang mata", "isenyas ang letrang ‘F’ sa magkabilang kamay at igalaw ang kanang kamay paikot sa kaliwang kamay pagkatapos ay itigil sa taas ng isang kamay", "isenyas ang letrang ‘E’ sa isang kamay at saka ito itapat sa babang bahagi ng tainga papunta sa kabila", "isenyas ang letrang ‘H’ at igalaw mula sa gilid ng noo papunta sa ng panga",
                //TRANSPORTASYON
                "ibuka na nakatihaya at  nakadikit ang magkabilang kamay at igalaw pasulong", "isenyas ang letrang ‘S’ sa magkabilang kamay at igalaw sa paikot na pasulong ng hindi nagsasabay", "isenyas ang letrang ‘B’ sa magkabilang kamay at igalaw ng magkasalungkat na taas at baba na direksyon", "ibuka ang palad sa kaliwang kamay nang nakahiwalay ang hinlalaki at isenyas ang letrang ‘J’ sa espasyo ng nakabukas na palad", "ibuka ang palad pahalang na nakahiwalay ang hinliliit at hinlalaki sa tatlong daliri at igalaw pahilig", "isenyas ang letrang ‘S’ na nakahiga sa magkabilang kamay at iangat na para bang nagmamaneho",
                //KASARIAN
                "isenyas ang letrang ‘A’ sa isang kamay, igalaw ito ng paunahan gamit ang hinlalaking daliri na sasayad sa pisngi", "itapat ng patagilid ang isang kamay sa noo at isenyas ang letrang ‘L’ na ang lahat ng daliri ay nakabukas. Igalaw ito ng pa sarado at bukas na galaw",
                //MIYEMBRO NG PAMILYA
                "ibuka ang palad gamit ang kanang kamay at idikit ang hinlalaki sa baba ", "ibuka ang palad gamit ang kanang kamay at idikit ang hinlalaki sa noo ", "isenyas ang letrang ‘S’ sa kaliwang kamay, itapat sa pisngi at isenyas ang letrang ‘D’ pasulong na pahiga ", "Isenyas ang letrang ‘B’ sa kaliwang kamay, itapat sa pisngi at isenyas pababa ang kamay ", "isensyas ang letrang ‘O’ sa kanang kamay, ilapat ang likod ng palad sa noo at ibuka ang mga daliri ng dalawang beses ", "isenyas ang letrang ‘O’ sa kanang kamay pahiga sa ilalim ng baba at ibuka ang mga daliri ng dalawang beses", "Pag patungin o pag higain sa isa’t isa ang dalawang palad at saka ito igalaw o galawin na para bang may dinuduyang sanggol",

                //PARIRALA
                "", "", "", "", "",
                "", "", "", "", "", "", "",
                "", "", "", "", "", "", "", "", "", "",
                "", "", "", "", "", "", "", "",
                "", "", "", "", "", ""
        };

        String[] youtubeId = new String[]{
                //ARAW NG LINGGO
                "nMCxbW6sbDs", "1pQch9y9PUQ", "rcRH24YFkQc", "DJpfrYJAC_M", "IZjo1Ahj0Dc", "fXdKxnHpbS8", "WJQBH4YPPAo",
                //MGA BUWAN
                "3B0EhpDIsWs", "lbVZMEygLPo", "GntFXCS2oXk", "j8H0ndK8GGI", "wfmyGv0MfP4", "0slrxjjS6mQ", "rUOiIQDxo5Y", "q5h4fWB8Des", "tXUoQULWnH4", "ndXKW1x3QNg", "roUUtHcLy0I", "AAat6bjCI60",
                //LUGAR
                "NMVLBcGmf44", "26QKxb_1Bm4", "0KbE355H_Q0",  "gHIQtOXTQ9s", "MP-Y_XMYMDg", "l8TRMw3NkKU", "l8TRMw3NkKU",
                //EMOSYON
                "DKGqDAQv9IE", "t28nM9AiYtk", "h-sTw1qvo70", "PmnseN7UZDQ", "zx4unH9NaBc", "ZcwzsGlnNmY", "aFK8BuvjZp4",
                //ALPABETO
                "0TlXVpqj0nM", "yIsWhpKh8yU", "0qMhUvMQ8V8", "KrBfZi8ogPI", "I5V0zBreaSc", "3DS33WTk7vg", "omFLAedvvAk", "3WuLSCtDp6Y", "CHspfSKx6KM", "vQGBp-Xb6I8", "EOVQBO5TSCs", "1EY21RyefsU", "ZLS4c30jpQY", "fQIOVjkxss4", "5xxOhedo84o", "LdZ_6cJ_5YA", "neaANip8Nq0", "bGVP94FxuxA", "kNI0Gqzaxxw", "KDWV5NItXbc", "3kbtMLBwzXA", "4Rm_orGU7Ds", "iX7OVUW2uus", "iEK4hwvYEXQ", "KQ4VzhYLpAs", "05hAeNWWLVU",
                //NUMERO
                "QpAUrFo8WfE", "OVexagWF3Rk", "YaFwBJH07Vc", "pwaV_jNkTxQ", "Dad9UrYXvh8", "hc0ExXXcQVI", "bZCQ_6kZ330", "FWr6jl9-wZA", "9LUwtmxri_k", "uPNt2fxMPWA", "BmyJbs_NQpU",
                //HAYOP
                "BApEDu9GHuU", "VhhR6s0g-pQ", "hQ7ZpR6eVMM", "yofItbqR0XU", "OYIvYV3Z8fs", "rKJA7QREqvU", "fjGHl1KoRVo",
                //HUGIS
                "P75kcPV6T6c", "wfoyj6nts2c", "9CkRrLkQBw4", "d0Xwq7DaVI0", "BQTpBZ1Q788",
                //KULAY
                "SptnckZ2bBU", "iDaDw2aC8LQ", "COmsG_EIj24", "oHVQaVQzGls", "a-ZsCrAVMyU", "zdf0r6XaXP8", "tzhi16G0oo0",
                //PRUTAS
                "1n-0govK-yY", "MNj2cEXvEkM", "gZj9Cis18A4", "F5GJm3MLlZg", "n-mnQPvoSwM", "UCmdb1vh0wM",
                //GULAY
                "YVVdYidPLkQ", "RLkd1UES2cg", "sBpvpCXT-To", "pnwD6N9mj5o", "j55ChxZZ4d8", "VuvAUrLeISQ", "vpXiCX0tJCM", "bPZSiVhYAP4", "WJUC_EcwZHY", "CHu_3K67Cgo", "OwQXmppp1CY",
                //PARTE NG KATAWAN
                "rpPAvgnoKOc", "mGpMqeffYcs", "IjvUMT1loNs", "wAqIsfqiFuk", "7e8GPATzKA0", "PFRYjaJ9XJY", "ZpuxcSm6Bx0", "SOk-eTAJeR8",
                //TRANSPORTASYON
                "-yhKPlUP16E", "0RZwvpkH4h0", "xvhswEPL7gs", "Yt_fN6r2Ysk", "KHtAD38fMnQ", "Y2F8OnViRME",
                //KASARIAN
                "pAhRlRHetR8", "f2XLgn2DoiM",
                //MIYEMBRO NG PAMILYA
                "ux-EPIqJO5w", "-N9ovBXHQt4", "sgEgS4YEaHk", "TA-E30JKVLQ", "TPqkixXxLl0", "6HqnVu4BpFk", "gpdtOhQcOAY",

                //MGA PARIRALA
                "kamusta_ka", "magandang_umaga_sa_iyo", "magandang_hapon_sa_iyo", "magandang_gabi_sa_iyo", "paalam_na_sayo",
                "tulungan_nyo_ako", "nananakawan_ako", "ako_ay_nawawala", "paki_bilisan_po", "anong_lugar_ito", "maaari_bang_humiram_ng_telepono", "paki_tigil_po",
                "ano_ang_pangalan_mo", "patawarin_mo_ako", "salamat_sa_iyo", "saan_ka_nakatira", "ingat_ka_sa_iyong_patutunguhan", "magandang_araw_sayo", "kain_tayo", "nauunawan_mo_ba_ako", "nasaan_ang_banyo", "nasaan_ang_kusina",
                "anong_oras_na", "pwede_mo_ba_sabihin_ang_oras", "alam_mo_ba_kung_anong_oras_na", "alas_sais_na_ng_umaga", "alas_dose_na_ng_tanghali", "alas_otso_na_ng_gabi", "magkita_tayo_mamaya", "magkita_tayo_bukas",
                "nandito_lang_ako_para_sayo", "ikaw_ay_isang_kaakit_akit_na_babae", "siya_ay_may_magandang_mata", "may_paghanga_ako_sayo", "gusto_kitang_yakapin", "gusto_kita"
        };

        String[] itemCategory = new String[itemName.length];
        String[] itemType = new String[itemName.length];
        int[] imagesNo = new int[itemName.length];

        for (int i = 0; i < itemName.length; i++){
            if (i < 7){
                itemCategory[i] = "Araw ng Linggo";
                itemType[i] = "Salita";

                if (i == 3){
                    imagesNo[i] = 2;
                }else {
                    imagesNo[i] = 1;
                }
            }else if (i < 19){
                itemCategory[i] = "Buwan";
                itemType[i] = "Salita";

                imagesNo[i] = 2;
            }else if (i < 26){
                itemCategory[i] = "Lugar";
                itemType[i] = "Salita";

                if (i == 20){
                    imagesNo[i] = 3;
                }else if (i == 24){
                    imagesNo[i] = 4;
                }else{
                    imagesNo[i] = 1;
                }
            }else if (i < 33){
                itemCategory[i] = "Emosyon";
                itemType[i] = "Salita";

                if (i == 27 || i == 31){
                    imagesNo[i] = 2;
                }else{
                    imagesNo[i] = 1;
                }
            }else if (i < 59){
                itemCategory[i] = "Alpabeto";
                itemType[i] = "Salita";

                imagesNo[i] = 1;
            }else if (i < 70){
                itemCategory[i] = "Numero";
                itemType[i] = "Salita";

                imagesNo[i] = 1;
            }else if (i < 77){
                itemCategory[i] = "Hayop";
                itemType[i] = "Salita";

                if (i == 70){
                    imagesNo[i] = 2;
                }else if (i == 74){
                    imagesNo[i] = 3;
                }else{
                    imagesNo[i] = 1;
                }
            }else if (i < 82){
                itemCategory[i] = "Hugis";
                itemType[i] = "Salita";

                imagesNo[i] = 1;
            }else if (i < 89){
                itemCategory[i] = "Kulay";
                itemType[i] = "Salita";

                if (i == 88){
                    imagesNo[i] = 2;
                }else{
                    imagesNo[i] = 1;
                }
            }else if (i < 95){
                itemCategory[i] = "Prutas";
                itemType[i] = "Salita";

                if (i == 92 || i == 93){
                    imagesNo[i] = 2;
                }else{
                    imagesNo[i] = 1;
                }
            }else if (i < 106){
                itemCategory[i] = "Gulay";
                itemType[i] = "Salita";

                if (i == 95 || i == 96 || i == 101 || i == 104){
                    imagesNo[i] = 2;
                }else if (i == 99){
                    imagesNo[i] = 3;
                }else{
                    imagesNo[i] = 1;
                }
            }else if (i < 114){
                itemCategory[i] = "Parte ng Katawan";
                itemType[i] = "Salita";

                if (i == 108 || i == 111){
                    imagesNo[i] = 4;
                }else if (i == 112){
                    imagesNo[i] = 2;
                }else{
                    imagesNo[i] = 1;
                }
            }else if (i < 120){
                itemCategory[i] = "Transportasyon";
                itemType[i] = "Salita";

                if (i == 119){
                    imagesNo[i] = 3;
                }else{
                    imagesNo[i] = 1;
                }
            }else if (i < 122){
                itemCategory[i] = "Kasarian";
                itemType[i] = "Salita";

                if (i == 120){
                    imagesNo[i] = 1;
                }else{
                    imagesNo[i] = 2;
                }
            }else if (i < 129){
                itemCategory[i] = "Miyembro ng Pamilya";
                itemType[i] = "Salita";

                if (i == 125 || i == 126 || i == 127){
                    imagesNo[i] = 2;
                }else{
                    imagesNo[i] = 1;
                }
            }else if (i < 134){
                itemCategory[i] = "Pagbati";
                itemType[i] = "Parirala";
            }else if (i < 141){
                itemCategory[i] = "Pang-Emergency";
                itemType[i] = "Parirala";
            }else if (i < 151){
                itemCategory[i] = "Pangkomunikasyon";
                itemType[i] = "Parirala";
            }else if (i < 159){
                itemCategory[i] = "Ekspresyon ng Oras";
                itemType[i] = "Parirala";
            }else {
                itemCategory[i] = "Ekspresyon ng Pagmamahal";
                itemType[i] = "Parirala";
            }
        }

        String[] partsOfSpeech = new String[]{
                "pangngalan", "pangngalan", "pangngalan", "pangngalan", "pangngalan", "pangngalan", "pangngalan",
                "pangngalan", "pangngalan", "pangngalan", "pangngalan", "pangngalan", "pangngalan", "pangngalan", "pangngalan", "pangngalan", "pangngalan", "pangngalan", "pangngalan",
                "pangngalan", "pangngalan", "pangngalan", "pangngalan", "pangngalan", "pangngalan", "pangngalan",
                "pang-uri", "pangngalan", "pandiwa", "pangngalan", "pangngalan", "pangngalan", "pangngalan",
                "Letrang A", "Letrang B", "Letrang C", "Letrang D", "Letrang E", "Letrang F", "Letrang G", "Letrang H", "Letrang I", "Letrang J", "Letrang K", "Letrang L", "Letrang M", "Letrang N", "Letrang O", "Letrang P", "Letrang Q", "Letrang R", "Letrang S", "Letrang T", "Letrang U", "Letrang V", "Letrang W", "Letrang X", "Letrang Y", "Letrang Z",
                "0","1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
                "pangngalan", "pangngalan", "pangngalan", "pangngalan", "pangngalan", "pangngalan", "pangngalan",
                "pangngalan", "pangngalan", "pangngalan", "pang-uri", "pangngalan",
                "pang-uri","pang-uri","pang-uri","pang-uri","pangngalan","pang-uri","pang-uri",
                "pangngalan","pangngalan","pangngalan","pangngalan","pangngalan","pangngalan",
                "pangngalan","pangngalan","pangngalan","pangngalan","pangngalan","pangngalan","pangngalan","pangngalan","pangngalan","pangngalan","pangngalan",
                "pangngalan","pangngalan","pangngalan","pangngalan","pangngalan","pangngalan","pangngalan","pangngalan",
                "pangngalan", "pangngalan", "pangngalan", "pangngalan"," pangngalan", "pangngalan",
                "pangngalan", "pangngalan",
                "pangngalan", "pangngalan", "pangngalan", "pangngalan"," pangngalan", "pangngalan", "pangngalan",
        };

        for (int i = 0; i < itemName.length; i++){
            ContentValues values = new ContentValues();
            values.put("itemName", itemName[i]);
            values.put("itemCategory", itemCategory[i]);
            values.put("itemType", itemType[i]);
            values.put("isLearned", 0);
            if (i < 129){
                values.put("partsOfSpeech", partsOfSpeech[i]);
            }else{
                values.put("partsOfSpeech", "");
            }
            values.put("imagesNo", imagesNo[i]);
            values.put("youtubeId", youtubeId[i]);
            values.put("howTo", howTo[i]);
            DB.insert("ItemTable", null, values);
        }

        //DATABASE FOR FAVORITE TABLE
        DB.execSQL("create Table IF NOT EXISTS FavoriteTable(itemName TEXT primary key," +
                " itemType TEXT)");

        //DATABASE FOR GAME TABLE
        DB.execSQL("create Table IF NOT EXISTS GameTable(userID INTEGER primary key," +
                " bestScore INTEGER, levelReached INTEGER)");

        ContentValues contentValues = new ContentValues();
        contentValues.put("userID", 201810336);
        contentValues.put("bestScore", 0);
        contentValues.put("levelReached", 0);
        DB.insert("GameTable", null, contentValues);

    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
        DB.execSQL("drop Table if exists CategoryTable");
        DB.execSQL("drop Table if exists ItemTable");
        DB.execSQL("drop Table if exists UserTable");
        DB.execSQL("drop Table if exists GameTable");
    }

    public Cursor getBadge(){
        SQLiteDatabase DB = this.getWritableDatabase();
        return DB.rawQuery("Select isBadge from UserTable where UserID=201810336", null);
    }

    public boolean updateBadge(int isBadge){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("isBadge", isBadge);
        try (Cursor cursor = DB.rawQuery("Select isBadge from UserTable where userID=201810336", null)){
            if (cursor.getCount() > 0){
                long result = DB.update("UserTable", values, "userID=201810336", null);
                return result != -1;
            }else{
                return false;
            }
        }

    }

    public Cursor getHighScore(){
        SQLiteDatabase DB = this.getWritableDatabase();
        return DB.rawQuery("Select * from GameTable where userID=201810336", null);
    }

    public boolean updateScore(int score, int levelReached){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("bestScore", score);
        values.put("levelReached", levelReached);
        try (Cursor cursor = DB.rawQuery("Select bestScore, levelReached from GameTable where userID=201810336", null)) {
            if (cursor.getCount() > 0) {
                long result = DB.update("GameTable", values, "userID=201810336", null);
                return result != -1;
            } else {
                return false;
            }
        }
    }

    public Cursor getUserData(String modifier){
        SQLiteDatabase DB = this.getWritableDatabase();

        switch (modifier) {
            case "GetNameStreak":
                return DB.rawQuery("Select userName, longestStreak from UserTable WHERE userID=201810336", null);
            case "GetLocale":
                return DB.rawQuery("Select selectedLanguage from UserTable WHERE userID=201810336", null);
            case "GetUserName":
                return DB.rawQuery("Select userName from UserTable where userID=201810336", null);
            case "StreakAvatarCard":
                return DB.rawQuery("Select currentStreak, longestStreak, avatarName from UserTable WHERE userID=201810336", null);
            case "Splash":
                return DB.rawQuery("Select dateToday, isOldUser from UserTable where userID=201810336", null);
            default:
                return DB.rawQuery("Select * from UserTable", null);
        }
    }

    public boolean updateSingleData (String value, int intValue, String modifier){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        switch (modifier) {
            case "currentDate":
                values.put("dateToday", value);
                try (Cursor cursor = DB.rawQuery("Select dateToday from UserTable where userID=201810336", null)) {
                    if (cursor.getCount() > 0) {
                        long result = DB.update("UserTable", values, "userID=201810336", null);
                        return result != -1;
                    } else {
                        return false;
                    }
                }
            case "UserName":
                values.put("userName", value);
                try (Cursor cursor = DB.rawQuery("Select userName from UserTable where UserID=201810336", null)) {
                    if (cursor.getCount() > 0) {
                        long result = DB.update("UserTable", values, "userID=201810336", null);
                        return result != -1;
                    } else {
                        return false;
                    }
                }
            case "Streak":
                values.put("currentStreak", intValue);
                try (Cursor cursor = DB.rawQuery("Select currentStreak from UserTable where UserID=201810336", null)) {
                    if (cursor.getCount() > 0) {
                        long result = DB.update("UserTable", values, "userID=201810336", null);
                        return result != -1;
                    } else {
                        return false;
                    }
                }
            case "Language":
                values.put("selectedLanguage", value);
                try (Cursor cursor = DB.rawQuery("Select selectedLanguage from UserTable where UserID=201810336", null)) {
                    if (cursor.getCount() > 0) {
                        long result = DB.update("UserTable", values, "userID=201810336", null);
                        return result != -1;
                    } else {
                        return false;
                    }
                }
            case "Avatar":
                values.put("avatarName", value);
                try (Cursor cursor = DB.rawQuery("Select avatarName from UserTable where UserID=201810336", null)) {
                    if (cursor.getCount() > 0) {
                        long result = DB.update("UserTable", values, "userID=201810336", null);
                        return result != -1;
                    } else {
                        return false;
                    }
                }
            case "updateCategoryProgress":
                values.put("categoryProgress", intValue);
                try (Cursor cursor = DB.rawQuery("Select categoryProgress from CategoryTable where categoryName=?", new String[]{value})) {
                    if (cursor.getCount() > 0) {
                        long result = DB.update("CategoryTable", values, "categoryName=?", new String[]{value});
                        return result != -1;
                    } else {
                        return false;
                    }
                }
            default:
                return false;
        }
    }

    public boolean UpdateMultipleData(int[] intValue, String[] stringValue, String modifier){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        if (modifier.equals("Streak")){
            values.put("currentStreak", intValue[0]);
            values.put("longestStreak", intValue[1]);
            try (Cursor cursor = DB.rawQuery("Select currentStreak, longestStreak from UserTable where UserID=201810336", null)){
                if (cursor.getCount()>0){
                    long result = DB.update("UserTable", values, "userID=201810336", null);
                    return result != -1;
                }else{
                    return false;
                }
            }
        }else if (modifier.equals("NewUser")){
            values.put("userName", stringValue[0]);
            values.put("selectedLanguage", stringValue[1]);
            values.put("avatarName", stringValue[2]);
            values.put("isOldUser", 1);

            try(Cursor cursor = DB.rawQuery("Select userName, selectedLanguage, avatarName, isOldUser from UserTable where UserID=201810336", null)){
                if (cursor.getCount()>0){
                    long result = DB.update("UserTable", values, "userID=201810336", null);
                    return result != -1;
                }else{
                    return false;
                }
            }
        }else {
            return false;
        }
    }

    public Cursor getCategory(String categoryType, String modifier){
        SQLiteDatabase DB = this.getWritableDatabase();
        if (modifier.equals("Learn")){
            return DB.rawQuery("Select categoryName, categoryColor, categoryTotalItems, categoryType from CategoryTable WHERE categoryType=? ORDER BY categoryName ASC", new String[]{categoryType});
        }else if (modifier.equals("getWordCategory")){
            return DB.rawQuery("Select categoryName from CategoryTable WHERE categoryType='Salita'", null);
        }
        else if (categoryType.equals("Salita") && modifier.equals("By5")){
            return DB.rawQuery("Select * from CategoryTable WHERE categoryType='Salita' AND categoryProgress != categoryTotalItems ORDER BY categoryProgress DESC LIMIT 5", null);
        }else if (categoryType.equals("Parirala") && modifier.equals("By5")){
            return DB.rawQuery("Select categoryName, categoryColor, imageURL from CategoryTable WHERE categoryType='Parirala' AND categoryProgress != categoryTotalItems ORDER BY categoryProgress DESC LIMIT 3", null);
        }else if (categoryType.equals("Salita") && modifier.equals("All")){
            return DB.rawQuery("Select * from CategoryTable WHERE categoryType='Salita' ORDER BY categoryProgress DESC LIMIT 5", null);
        }else if (categoryType.equals("Parirala") && modifier.equals("All")){
            return DB.rawQuery("Select categoryName, categoryColor, imageURL from CategoryTable WHERE categoryType='Parirala' ORDER BY categoryProgress DEsC LIMIT 3", null);
        }
        else if (modifier.equals("Profile")){
            return DB.rawQuery("Select * from CategoryTable ORDER BY categoryName ASC LIMIT 5", null);
        }else if (modifier.equals("Search")){
            return DB.rawQuery("Select categoryName, categoryType from CategoryTable ORDER By categoryName ASC", null);
        }else if (modifier.equals("SingleCategory")){
            return DB.rawQuery("Select categoryColor, categoryTotalItems, imageURL, categoryProgress from CategoryTable WHERE categoryName=?", new String[]{categoryType});
        }else {
            return DB.rawQuery("Select * from CategoryTable ORDER BY categoryName ASC", null);
        }
    }

    public Cursor getItem(String value, String modifier){
        SQLiteDatabase DB = this.getWritableDatabase();
        switch (modifier) {
            case "SearchItem":
                return DB.rawQuery("Select itemName from ItemTable WHERE itemType=? ORDER BY itemName ASC", new String[]{value});
            case "ItemList":
                return DB.rawQuery("Select itemName, isLearned from ItemTable WHERE itemCategory=?", new String[]{value});
            case "getCategoryProgress":
                return DB.rawQuery("Select categoryProgress from CategoryTable WHERE categoryName=?", new String[]{value});
            case "getItemCategory":
                return DB.rawQuery("Select itemCategory, isLearned from ItemTable WHERE itemName=?", new String[]{value});
            case "getItemHeart":
                return DB.rawQuery("Select * from FavoriteTable WHERE itemName=?", new String[]{value});
            case "Favorite":
                return DB.rawQuery("Select * from FavoriteTable", null);
            case "getWordOfTheDay":
                return DB.rawQuery("Select itemNameWOTD from UserTable WHERE UserID=201810336", null);
            case "getItemByCategory":
                return DB.rawQuery("Select * from ItemTable WHERE itemCategory=?", new String[]{value});
            case "getWOTDItem":
                return DB.rawQuery("Select * from ItemTable WHERE itemType='Salita'", null);
            case "getYoutubeId":
                return DB.rawQuery("Select youtubeId from ItemTable WHERE itemName=?", new String[]{value});
            default:
                return DB.rawQuery("Select * from ItemTable WHERE itemName=?", new String[]{value});
        }
    }

    public boolean UpdateItem (String stringValue, int intValue, String modifier){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        if (modifier.equals("isLearned")){
            values.put("isLearned", intValue);

            try(Cursor cursor = DB.rawQuery("Select isLearned from ItemTable where itemName=?", new String[]{stringValue})){
                if (cursor.getCount()>0){
                    long result = DB.update("ItemTable", values, "itemName=?", new String[]{stringValue});
                    return result != -1;
                }else{
                    return false;
                }
            }
        }else if (modifier.equals("NewWordOfTheDay")){
            values.put("itemNameWOTD", intValue);

            try(Cursor cursor = DB.rawQuery("Select itemNameWOTD from UserTable where UserID=201810336", null)){
                if (cursor.getCount()>0){
                    long result = DB.update("UserTable", values, "UserID=201810336", null);
                    return result != -1;
                }else{
                    return false;
                }
            }
        }else {
            return false;
        }
    }

    public boolean newFavorite(String itemName, String itemType, String modifier){
        SQLiteDatabase DB = this.getWritableDatabase();

        if (modifier.equals("Add")){
            ContentValues contentValues = new ContentValues();
            contentValues.put("itemName", itemName);
            contentValues.put("itemType", itemType);
            long result = DB.insert("FavoriteTable", null, contentValues);
            return result != -1;
        }else if (modifier.equals("Remove")){
            try (Cursor cursor = DB.rawQuery("Select * from FavoriteTable where itemName =?", new String[]{itemName})) {
                if (cursor.getCount() > 0) {
                    long result = DB.delete("FavoriteTable", "itemName=?", new String[]{itemName});
                    return result != -1;
                } else {
                    return false;
                }
            }
        } else {
            return false;
        }
    }

    public Cursor countItems(String modifier){
        SQLiteDatabase DB = this.getWritableDatabase();
        switch (modifier) {
            case "WordDiscovered":
                return DB.rawQuery("Select COUNT(*) FROM ItemTable WHERE isLearned='1' AND itemType='Salita'", null);
            case "PhraseDiscovered":
                return DB.rawQuery("Select COUNT(*) FROM ItemTable WHERE isLearned='1' AND itemType='Parirala'", null);
            case "FavoriteCount":
                return DB.rawQuery("Select COUNT(*) from FavoriteTable", null);
            default:
                return null;
        }
    }


}
