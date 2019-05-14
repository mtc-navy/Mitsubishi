/**
 * Copyright 2018 MTC http://www.mtcsys.com/
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package io.mtc.modules.sys.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.mtc.common.utils.PageUtils;
import io.mtc.common.utils.Query;
import io.mtc.modules.sys.dao.SysDictDao;
import io.mtc.modules.sys.entity.SysDictEntity;
import io.mtc.modules.sys.service.SysDictService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import sun.swing.StringUIClientPropertyKey;

import java.util.Map;


@Service("sysDictService")
public class SysDictServiceImpl extends ServiceImpl<SysDictDao, SysDictEntity> implements SysDictService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String name = (String) params.get("name");
        if (StringUtils.isEmpty(name)) {
            name = "";
        }
        String type = (String) params.get("type");

        Page<SysDictEntity> page;
        if (StringUtils.isBlank(type)) {
            page = this.selectPage(
                    new Query<SysDictEntity>(params).getPage(),
                    new EntityWrapper<SysDictEntity>()
                            .like(StringUtils.isNotBlank(name), "type||name", name)
                            .orderBy("type")
            );
        } else {
            page = this.selectPage(
                    new Query<SysDictEntity>(params).getPage(),
                    new EntityWrapper<SysDictEntity>()
                            .like(StringUtils.isNotBlank(name), "type||name", name)
                            .eq(StringUtils.isNotBlank(type), "type", type)
                            .orderBy("type")
            );
        }

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPageWithCode(Map<String, Object> params) {
        String code = (String) params.get("code");
        String type = (String) params.get("type");

        Page<SysDictEntity> page = this.selectPage(
                new Query<SysDictEntity>(params).getPage(),
                new EntityWrapper<SysDictEntity>()
                        .eq(StringUtils.isNotBlank(code), "code", code)
                        .eq(StringUtils.isNotBlank(type), "type", type)
        );

        return new PageUtils(page);
    }

}
