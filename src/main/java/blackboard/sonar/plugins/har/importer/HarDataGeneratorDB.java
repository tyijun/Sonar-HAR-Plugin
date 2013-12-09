package blackboard.sonar.plugins.har.importer;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.config.Settings;
import org.sonar.api.resources.Project;

import blackboard.sonar.plugins.definition.Har;
import blackboard.sonar.plugins.definition.HarMetrics;
import blackboard.sonar.plugins.har.HarPluginConst;

public class HarDataGeneratorDB extends HarDataGenerator {

	private static final Logger LOG = LoggerFactory
			.getLogger(HarDataGeneratorDB.class);
	private DateFormat dateFormat = new SimpleDateFormat(
			"yyyy-MM-dd_HH-mm-ss_SSS");
	private Settings settings;

	public HarDataGeneratorDB(Settings settings, Project project) {
		super(project);
		this.settings = settings;
	}

	@Override
	public void generateResultFiles() {
		// TODO Auto-generated method stub
		try {
			int random = 1 + (int) (Math.random() * 100);
			Date date = new Date();
			Map<String, String> map = new HashMap<String, String>();
			map.put(HarMetrics.RESP_TIME_MIN.getKey(), "2.5");
			map.put(HarMetrics.RESP_TIME_MAX.getKey(), "8.5");
			map.put(HarMetrics.RESP_TIME_AVG.getKey(), "4.5");
			map.put(HarMetrics.RESP_TIME_STDEV.getKey(), "3.5");
			map.put(HarMetrics.TOTAL_HTTP400.getKey(), "100");
			map.put(HarMetrics.TOTAL_HTTP500.getKey(), "120");
			map.put(HarMetrics.AVG_HTTP400.getKey(), "60");
			map.put(HarMetrics.AVG_HTTP500.getKey(), "80");
			map.put(HarMetrics.BUILD.getKey(), "9.1.160062.0");
			map.put(HarMetrics.IMPORT_TIME.getKey(), dateFormat.format(date));
			String key = "HAR_" + dateFormat.format(date) + "_" + random;
			File directory = new File(
					settings.getString(HarPluginConst.PROEJCT_BASE_DIR)
							+ settings
									.getString(HarPluginConst.PROEJCT_FILE_SEPARATOR)
							+ settings
									.getString(HarPluginConst.SOURCE_DIRECTORY));
			File file = new File(directory,key+"."+Har.KEY);
			convertHarDataToFile(file,map);
		} catch (Exception ex) {
			LOG.error("Error when building data from DB:", ex);
		}
	}

}
