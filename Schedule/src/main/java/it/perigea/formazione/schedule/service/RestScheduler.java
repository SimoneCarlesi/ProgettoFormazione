package it.perigea.formazione.schedule.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import it.perigea.formazione.schedule.dto.InputParamsDto;
import it.perigea.formazione.schedule.dto.ScheduleDto;
import it.perigea.formazione.schedule.dto.URLDto;
import it.perigea.formazione.schedule.entity.CronEntity;
import it.perigea.formazione.schedule.entity.ScheduleEntity;
import it.perigea.formazione.schedule.entity.URLEntity;
import it.perigea.formazione.schedule.repository.CronRepository;
import it.perigea.formazione.schedule.repository.ScheduleRepository;
import it.perigea.formazione.schedule.repository.URLRepository;
import it.perigea.formazione.schedule.runnables.RunnableTask;



@Service
public class RestScheduler {
	private static final Logger logger = LoggerFactory.getLogger(RestScheduler.class);

	public static final Map<String, ScheduledFuture<?>> map = new HashMap<>();
	public static final Map<String, ScheduleEntity> mapSchedule = new HashMap<>();

	@Value(value = "${CRON_ID}")
	private Integer cronId;

	@Autowired
	private CronRepository cronRepository;

	@Autowired
	private URLRepository urlRepository;

	@Autowired
	private ThreadPoolTaskScheduler scheduler;

	@Autowired
	private ScheduleRepository scheduleRep;

	// Start a Schedule Task
	public ScheduledFuture<?> startSchedule(InputParamsDto params) {
		ScheduleDto scheduleDto = new ScheduleDto();
		Date date = new Date();
		String APIMethodName = params.getAPIMethodName();
		String parametro = params.getParam();
		RunnableTask task = new RunnableTask(getUrlEntity(APIMethodName), parametro);
		String cronString = cronRepository.findByIdCron(cronId).getCronString();
		CronTrigger cron = new CronTrigger(cronString);
		ScheduledFuture<?> future = scheduler.schedule(task, cron);
		map.put(APIMethodName, future);
		scheduleDto.setAPIMethodName(APIMethodName);
		scheduleDto.setBegginingDate(date);
		scheduleDto.setStatus("running");
		ScheduleEntity scheduleEntity = saveScheduleProcess(scheduleDto);
		mapSchedule.put(APIMethodName, scheduleEntity);
		return future;
	}

	/**Stop a Schedule Task
	 * 
	 * @param APIMethodName
	 */
	public void stopSchedule(String APIMethodName) {
		if (map.containsKey(APIMethodName)) {
			ScheduledFuture<?> future = map.get(APIMethodName);
			future.cancel(true);
			ScheduleEntity scheduleEntity = mapSchedule.get(APIMethodName);
			scheduleEntity.setStatus("arrested");
			scheduleRep.save(scheduleEntity);
			logger.info("La schedule è stata interrotta, sono in arrivo in console gli ultimi dati estratti");
		}
	}

	// Modify the CronTrigger
	public void modifyScheduleTrigger(String cronString) {
		CronEntity cron = cronRepository.findByIdCron(cronId);
		cron.setCronString(cronString);
		cronRepository.save(cron);
		logger.info("Il cronTrigger è stato modificato");

	}

	// Insert a new URL string in a DB
	public URLEntity insertURL(URLDto urlDto) {
		URLEntity urlEntity = new URLEntity();
		urlEntity.setAPIMethodName(urlDto.getAPIMethodName());
		urlEntity.setURLString(urlDto.getURLString());
		urlEntity.setReturnType(urlDto.getReturnType());
		urlRepository.save(urlEntity);
		logger.info("Url inserito correattamente a db");
		return urlEntity;
	}

	// get from DB a URL String and transform into a URI type
	public URLEntity getUrlEntity(String APIMethodName) {
		List<URLEntity> urlEntityList = urlRepository.findByAPIMethodName(APIMethodName);
		URLEntity urlEntity = urlEntityList.get(0);
		return urlEntity;
	}

	public void stopScheduler() {
		ScheduledExecutorService scheduledExecutorService = scheduler.getScheduledExecutor();
		scheduledExecutorService.shutdownNow();
	}

	
	public ScheduleEntity saveScheduleProcess(ScheduleDto dto) {
		ScheduleEntity scheduleEntity = new ScheduleEntity();
		scheduleEntity.setAPIMethodName(dto.getAPIMethodName());
		scheduleEntity.setBegginingDate(dto.getBegginingDate());
		scheduleEntity.setStatus(dto.getStatus());
		scheduleRep.save(scheduleEntity);
		return scheduleEntity;
	}

}