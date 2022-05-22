package com.wyn.crowd.service.impl;

import com.wyn.crowd.mapper.MenuMapper;
import com.wyn.crowd.mapper.entity.Menu;
import com.wyn.crowd.mapper.entity.MenuExample;
import com.wyn.crowd.service.api.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wei-ya-nan
 * @version 1.0
 * @date 2022/3/20
 */
@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    public List<Menu> getAll() {
        return menuMapper.selectByExample(null);
    }

    public void saveMenu(Menu menu) {
        menuMapper.insert(menu);
    }

    public void updateMenu(Menu menu) {
        // 因为pid的值是空的需要用有选择的更新条件
        menuMapper.updateByPrimaryKeySelective(menu);
    }

    public void removeMenu(Integer id) {
        menuMapper.deleteByPrimaryKey(id);
    }
}
