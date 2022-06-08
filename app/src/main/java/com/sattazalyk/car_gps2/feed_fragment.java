package com.sattazalyk.car_gps2;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.sattazalyk.car_gps2.model.Post;

import java.util.ArrayList;

public class feed_fragment extends Fragment {

    RecyclerView rv_feed;

    public void initializationUI(View view) {
        rv_feed = view.findViewById(R.id.rv_feed);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feed, container, false);
        initializationUI(view);

        ArrayList<Post> posts = new ArrayList<>();

        posts.add(new Post("Первая новость",
                "Компания ТОО «Сәт Тазалық» предоставляет\n" +
                        "широкий спектр услуг по уборке и вывозу мусора\n" +
                        "с жилых районов и с территории частных организаций.",
                "https://scontent.fpwq4-1.fna.fbcdn.net/v/t39.30808-6" +
                        "/231147078_4143441005746516_4495089904882065766_n.jpg?stp" +
                        "=dst-jpg_p526x296&_nc_cat=108&ccb=1-7&_nc_sid=a26aad&_nc_ohc" +
                        "=Iq9pXjUD4l4AX_mEC0c&_nc_ht=scontent.fpwq4-1.fna&oh=00_AT_Ql_X" +
                        "pQgVqvlH10NQEgVh1GLKXn3NuulF-QIC99EgQ6A&oe=62A3F0A8"));

        posts.add(new Post("С днем государственных\n              символов!",
                "В 1992 году 4 июня были впервые утверждены\n" +
                        "новые государственные символы независимого\n" +
                        "Казахстана. С того времени эта дата является\n" +
                        "Днем рождения новой государственной символики.\n" +
                        "В этот знаменательный день желаем вам, счастья,\n" +
                        "неисчерпаемой энергии, жизненного оптимизма и новых\n" +
                        "достижений на благо любимой республики!",
                "https://kazakhstan-kmv.ru/wp-content/uploads/2019/06/%D0%BA%D0%B0%D1%80%D1%82%D0%B8%D0%BD%D0%BA%D0%B0-4-%D0%B8%D1%8E%D0%BD%D1%8F.png"
                ));
        posts.add(new Post("Обращение потребителям",
                "В случае если у вас во дворе переполненна мусорные контейнеры,\n" +
                        "раскинут мусор или вас беспокоят другие жилищно-коммунальные проблемы,\n" +
                        "пишите в Direct нашего Instagram-аккаунта @sat-tazalyk\n" +
                        "или позвоните на наши контактные телефоны:\n" +
                        "+8(71063)37044,\n" +
                        "+8(7102)776605.",
                "https://i.ytimg.com/vi/arPZI4eo5wM/maxresdefault.jpg"));


        for (int i = 1; i <= 10; i++) {
            posts.add(new Post("Header " + i, "TEXT", "https://picsum.photos/600/300?random&" + i));
        }

        rv_feed.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv_feed.setAdapter(new FeedAdapter(posts));

        return view;
    }

} /*"https://scontent.fpwq4-1.fna.fbcdn.net" +
                        "/v/t1.6435-9/206196714_4027936260630325_" +
                                "4594770742586689004_n.jpg?_nc_cat=108&ccb=" +
                                "1-7&_nc_sid=a26aad&_nc_ohc=sszMRqPTd2cAX8SNCI" +
                                "t&_nc_ht=scontent.fpwq4-1.fna&oh=00_AT9pQgxTA1C1" +
                                "pDgDvvMXzVvOdx_4okhuSByEurPTWdAw6g&oe=62C39909"*/