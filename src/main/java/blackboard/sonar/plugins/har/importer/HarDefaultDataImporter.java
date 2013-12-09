package blackboard.sonar.plugins.har.importer;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.sonar.api.batch.SensorContext;
import org.sonar.api.config.Settings;
import org.sonar.api.measures.Measure;
import org.sonar.api.resources.Project;

import blackboard.sonar.plugins.har.HarPluginConst;
import blackboard.sonar.plugins.har.exception.HarPluginException;
import blackboard.sonar.plugins.har.resources.HarResource;

public class HarDefaultDataImporter implements HarDataImporter {

	public static final String DATA_COME_FROM_DB = "DB";
	public static final String DATA_COME_FROM_FILE = "FILE";
	public static final String DATA_COME_FROM_WEBAPI = "WEBAPI";
	private Settings settings;
	private Project project;
	private SensorContext sensorContext;

	private HarDefaultDataImporter(Settings settings, Project project,
			SensorContext sensorContext) {
		this.settings = settings;
		this.project = project;
		this.sensorContext = sensorContext;
	}

	public static HarDefaultDataImporter getInstance(Settings settings,
			Project project, SensorContext sensorContext) {
		HarDefaultDataImporter importer = new HarDefaultDataImporter(settings,
				project, sensorContext);
		importer.initialize();
		return importer;
	}

	public void importData() throws HarPluginException {
		// TODO Auto-generated method stub
		Map<HarResource, List<Measure>> data = null;
		try {
			if (StringUtils.equals(
					settings.getString(HarPluginConst.DATASOURCE),
					DATA_COME_FROM_DB)) {
				data = new HarDBDataProvider(
						settings.getString(HarPluginConst.DB_DRIVER),
						settings.getString(HarPluginConst.DB_JDBCURL),
						settings.getString(HarPluginConst.DB_USERNAME),
						settings.getString(HarPluginConst.DB_PASSWORD))
						.buildData();
			} else if (StringUtils.equals(
					settings.getString(HarPluginConst.DATASOURCE),
					DATA_COME_FROM_FILE)) {
				data = new HarFileDataProvider(settings, project).buildData();
			} else if (StringUtils.equals(
					settings.getString(HarPluginConst.DATASOURCE),
					DATA_COME_FROM_WEBAPI)) {
				data = new HarWebApiDataProvider().buildData();
			}
			if (data != null && data.size() > 0) {
				for (Map.Entry<HarResource, List<Measure>> entry : data
						.entrySet()) {
					HarResource resource = entry.getKey();
					List<Measure> measures = entry.getValue();
					if (measures != null && measures.size() > 0) {
						sensorContext.index(resource);
						for (Measure measure : measures) {
							// sensorContext.saveMeasure(resource, measure);
							resource.addMeasure(measure);
						}
						if (settings
								.getBoolean(HarPluginConst.SAVE_RESULT_AS_FILE)) {
							String source = convertDataToSource(measures);
							if (StringUtils.isNotEmpty(source)) {								
								sensorContext.saveSource(resource, source);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			throw new HarPluginException("Importing HAR Data error", e);
		}

	}

	protected void initialize() {
		// Add initialization logic here...
	}

	private String convertDataToSource(List<Measure> measures) {
		StringBuffer sb = new StringBuffer();
		int i = 0;
		if (measures != null && measures.size() > 0) {
			for (Measure measure : measures) {
				if (i > 0) {
					sb.append("\r\n");
				}
				sb.append(measure.getMetric().getKey() + "=" + measure.getData());
				i++;
			}
		}
		return sb.toString();
	}
}
