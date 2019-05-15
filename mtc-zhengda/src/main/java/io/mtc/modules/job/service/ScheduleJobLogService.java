

package io.mtc.modules.job.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.mtc.common.utils.PageUtils;
import io.mtc.modules.job.entity.ScheduleJobLogEntity;

import java.util.Map;

/**
 * 定时任务日志
 *
 * @author Mark sunlightcs@gmail.com
 */
public interface ScheduleJobLogService extends IService<ScheduleJobLogEntity> {

	PageUtils queryPage(Map<String, Object> params);
	
}
