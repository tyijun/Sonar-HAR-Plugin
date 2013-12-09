package blackboard.sonar.plugins.har.batch;

import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.CharEncoding;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.batch.Sensor;
import org.sonar.api.batch.SensorContext;
import org.sonar.api.config.Settings;
import org.sonar.api.resources.Project;

import blackboard.sonar.plugins.definition.Har;
import blackboard.sonar.plugins.har.importer.HarDataImporter;
import blackboard.sonar.plugins.har.importer.HarDefaultDataImporter;

public class HarSensor implements Sensor {

	private static final Logger LOG = LoggerFactory.getLogger(HarSensor.class);
	private static final String CONF_PATH = "conf/har_conf.properties";
	private Settings settings;

	/**
	 * Use of IoC to get Settings
	 */
	public HarSensor(Settings settings) {
		this.settings = settings;
	}

	public boolean shouldExecuteOnProject(Project project) {
		// Execute only on HAR projects
		return StringUtils.equals(project.getLanguageKey(), Har.KEY);
	}

	public void analyse(Project project, SensorContext sensorContext) {
		try {
			HarDataImporter importer = HarDefaultDataImporter.getInstance(
					settings, project, sensorContext);
			importer.importData();
		} catch (Exception e) {
			LOG.error("Analyzing on HAR Plugin error", e);
		}
	}

	@Override
	public String toString() {
		return getClass().getSimpleName();
	}

	protected void appendCustomProperties() {
		// Setting the custom properties about HAR plugin
		Reader reader = null;
		try {
			reader = new InputStreamReader(HarSensor.class.getClassLoader()
					.getResourceAsStream(CONF_PATH),
					Charset.forName(CharEncoding.UTF_8));
			Properties prop = new Properties();
			prop.load(reader);
			Iterator<Entry<Object, Object>> it = prop.entrySet().iterator();
			while (it.hasNext()) {
				Entry<Object, Object> obj = it.next();
				if (!settings.hasKey(obj.getKey().toString())) {
					settings.appendProperty(obj.getKey().toString(), obj
							.getValue().toString());
				}
			}
		} catch (Exception e) {
			LOG.warn(e.getLocalizedMessage());
		} finally {
			IOUtils.closeQuietly(reader);
		}
	}

}
