package com.ksintership.kozhushanmariia.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.DrawableRes;


import com.ksintership.kozhushanmariia.R;

import com.ksintership.kozhushanmariia.app.Genre;
import com.ksintership.kozhushanmariia.app.MovieMeadow;
import com.ksintership.kozhushanmariia.fragments.FragmentViewer;
import com.ksintership.kozhushanmariia.utils.Constants;
import com.ksintership.kozhushanmariia.fragments.FragmentChooser;
import com.ksintership.kozhushanmariia.utils.listeners.GenreSelectListener;


public class MainActivity extends BaseActivity {

    private FragmentChooser fragmentChooser;
    private FragmentViewer fragmentViewer;

    private GenreSelectListener genreSelectListener;

    boolean inLandscapeMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createBaseToolbar(getString(R.string.app_name));

        inLandscapeMode = findViewById(R.id.fragment_two) != null;
        fragmentChooser = (FragmentChooser) getSupportFragmentManager().findFragmentById(R.id.fragment_one);
        if (inLandscapeMode) {
            fragmentViewer = (FragmentViewer) getSupportFragmentManager().findFragmentById(R.id.fragment_two);
        }
        genreSelectListener = new GenreSelectListener() {

            @Override
            public void onCOMEDYSelected() {
                displayDescriptionOfSelected(R.drawable.comedy, "Мальчишник в Вегасе", Genre.COMEDY.toString(), 7.9, 2009, "Они мечтали устроить незабываемый мальчишник в Вегасе. " +
                        "То, что парни вытворяли на вечеринке, не идет ни в какое сравнение с тем, что им придется сделать на трезвую голову, " +
                        "когда они будут шаг за шагом восстанавливать события прошлой ночи.");
            }

            @Override
            public void onDOCUMENTARYSelected() {
                displayDescriptionOfSelected(R.drawable.documentary, "Кобейн: Чёртов монтаж", Genre.DOCUMENTARY.toString(), 7.7,
                        2015, "Взгляд на жизнь, творчество и мысли Курта Кобейна, кумира целого поколения и одной из ярчайших фигур конца XX века. " +
                                "«Чёртов монтаж» отслеживает творческий путь Кобейна от самых ранних начинаний в городе Абердин, штат Вашингтон, до зенита всемирной славы – и находит за титанической фигурой легенды мировой рок-музыки уязвимого человека, жившего в вечной борьбе с миром вокруг.");
            }


            @Override
            public void onDRAMASelected() {
                displayDescriptionOfSelected(R.drawable.drama, "Побег из Шоушенка", Genre.DRAMA.toString(), 9.1,
                        1994, "Бухгалтер Энди Дюфрейн обвинён в убийстве собственной жены и её любовника. Оказавшись в тюрьме под названием Шоушенк, он сталкивается с жестокостью и беззаконием, царящими по обе стороны решётки. Каждый, кто попадает в эти стены, становится их рабом до конца жизни. " +
                                "Но Энди, обладающий живым умом и доброй душой, находит подход как к заключённым, так и к охранникам, добиваясь их особого к себе расположения.");

            }

            @Override
            public void onADVENTURESelected() {
                displayDescriptionOfSelected(R.drawable.adventure, "Властелин колец", Genre.ADVENTURE.toString(), 8.6,
                        2001, "Сказания о Средиземье - это хроника Великой войны за Кольцо, войны, длившейся не одну тысячу лет. Тот, кто владел Кольцом, получал власть над всеми живыми тварями, но был обязан служить злу.\n" +
                                "\n" + "Однако оно было утеряно. Теперь владыка кольца Саурон хочет вернуть себе власть над Средиземьем. " +
                                "Хоббит Бильбо отдает найденное кольцо своему племяннику Фродо, который пытаетсяс Братством Кольца научиться справляться с тем страшным могуществом, которое дает ему кольцо…");

            }

            @Override
            public void onTHRILLERSelected() {
                displayDescriptionOfSelected(R.drawable.thriller, "Семь", Genre.THRILLER.toString(), 8.3,
                        1995, "Детектив Уильям Сомерсет - ветеран уголовного розыска, мечтающий уйти на пенсию и уехать подальше от города и грешных обитателей. За 7 дней до пенсии на Сомерсета сваливаются две неприятности: молодой напарник Миллс и особо изощренное убийство. " +
                                "Поняв, что убийца наказывает свои жертвы за совершенные ими смертные грехи, детектив встает перед выбором: вернуться к работе либо уйти и передать дело своему менее опытному напарнику?");
            }

            @Override
            public void onCARTOONSelected() {
                displayDescriptionOfSelected(R.drawable.cartoon, "Король Лев", Genre.CARTOON.toString(), 8.8,
                        1994, " У величественного Короля-Льва Муфасы рождается наследник по имени Симба. Уже в детстве любознательный малыш становится жертвой интриг своего завистливого дяди Шрама, мечтающего о власти.\n" +
                                "\n" + "Симба познаёт горе утраты, предательство и изгнание, но в конце концов обретает верных друзей и находит любимую. Закалённый испытаниями, он в нелёгкой борьбе отвоёвывает своё законное место в «Круге жизни», осознав, что значит быть настоящим Королём.");

            }


            @Override
            public void onDETECTIVESelected() {
                displayDescriptionOfSelected(R.drawable.detective, "Остров проклятых", Genre.DETECTIVE.toString(), 8.5,
                        2009, "  Два американских судебных пристава отправляются на один из островов в штате Массачусетс, чтобы расследовать исчезновение пациентки клиники для умалишенных преступников. " +
                                "При проведении расследования им придется столкнуться с паутиной лжи, обрушившимся ураганом и смертельным бунтом обитателей клиники.");

            }

            @Override
            public void onFANRASYSelected() {
                displayDescriptionOfSelected(R.drawable.fantasy, "Гарри Поттер", Genre.FANTASY.toString(), 8.2,
                        2001, "Жизнь десятилетнего Гарри Поттера нельзя назвать сладкой: родители умерли, едва ему исполнился год, а от дяди и тёти, взявших сироту на воспитание, достаются лишь тычки да подзатыльники. Но в одиннадцатый день рождения Гарри всё меняется." +
                                "Через пару недель Гарри будет мчаться в поезде Хогвартс-экспресс навстречу новой жизни, где его ждут невероятные приключения, верные друзья и самое главное — ключ к разгадке тайны смерти его родителей.");

            }


            @Override
            public void openThirdActivity() {
                Intent viewIntent = new Intent(MainActivity.this, ThirdActivity.class);
                startActivity(viewIntent);
            }
        };

        fragmentChooser.setPictureSelectListener(genreSelectListener);

    }

    private void displayDescriptionOfSelected(@DrawableRes int selectedImageResId, String name, String genre, double rating, int year, String description) {
//MovieMeadow movieMeadow= new MovieMeadow(name,  genre, rating, year,  description);
        System.out.println(inLandscapeMode);
        if (inLandscapeMode) {
            fragmentViewer.displayResource(selectedImageResId, name, genre, rating, year, description);
        } else {
            Intent viewIntent = new Intent(MainActivity.this, SecondActivity.class);
            viewIntent.putExtra(Constants.KEY_IM_ID, selectedImageResId);
            viewIntent.putExtra(Constants.KEY_NM_ID, name);
            viewIntent.putExtra(Constants.KEY_GR_ID, genre);
            viewIntent.putExtra(Constants.KEY_RT_ID, rating);
            viewIntent.putExtra(Constants.KEY_YR_ID, year);
            viewIntent.putExtra(Constants.KEY_DC_ID, description);
            startActivity(viewIntent);
        }
    }
}