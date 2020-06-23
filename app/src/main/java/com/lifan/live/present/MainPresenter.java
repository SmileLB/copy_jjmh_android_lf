package com.lifan.live.present;
import com.lifan.base.mvp.BasePresenter;
import com.lifan.base.mvp.BaseRepository;
import com.lifan.base.router.RouterMap;



public class MainPresenter extends BasePresenter<BaseRepository, MainContract.IMainView> implements MainContract.IMainPresenter {

    private String fragmentPath[] = new String[]
            {
            RouterMap.COMIC_HOME_FRAGMENT,
            RouterMap.COMIC_FIND_FRAGMENT,
            RouterMap.COMIC_SEARCH_FRAGMENT,
            RouterMap.COMIC_BOOKSHELF_FRAGMENT,
            RouterMap.COMIC_MINE_FRAGMENT
            };

}
