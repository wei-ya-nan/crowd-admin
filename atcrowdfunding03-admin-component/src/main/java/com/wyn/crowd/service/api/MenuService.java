package com.wyn.crowd.service.api;

import com.wyn.crowd.mapper.entity.Menu;

import java.util.List;

/**
 * @author wei-ya-nan
 * @version 1.0
 * @date 2022/3/20
 */
public interface MenuService {
    List<Menu> getAll();

    void saveMenu(Menu menu);

    void updateMenu(Menu menu);

    void removeMenu(Integer id);
}
