package blackboard.sonar.plugins.har.importer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.sonar.api.batch.SensorContext;
import org.sonar.api.config.Settings;
import org.sonar.api.measures.Measure;
import org.sonar.api.resources.Project;

import blackboard.sonar.plugins.definition.HarMetrics;
import blackboard.sonar.plugins.har.HarPluginConst;
import blackboard.sonar.plugins.har.exception.HarPluginException;

public class HarSimpleDataImporter implements HarDataImporter {

	private Settings settings;
	private Project project;
	private SensorContext sensorContext;
	private static final String query = "select * from har.getallmetrics";

	private HarSimpleDataImporter(Settings settings, Project project,
			SensorContext sensorContext) {
		this.settings = settings;
		this.project = project;
		this.sensorContext = sensorContext;
	}

	public static HarSimpleDataImporter getInstance(Settings settings,
			Project project, SensorContext sensorContext) {
		HarSimpleDataImporter importer = new HarSimpleDataImporter(settings,
				project, sensorContext);
		importer.initialize();
		return importer;
	}

	public void importData() throws HarPluginException {
		// TODO Auto-generated method stub
		try {
			String buildNum = settings
					.getString(HarPluginConst.PROEJCT_VERSION);
			String driver = settings.getString(HarPluginConst.DB_DRIVER);
			String jdbcurl = settings.getString(HarPluginConst.DB_JDBCURL);
			String username = settings.getString(HarPluginConst.DB_USERNAME);
			String password = settings.getString(HarPluginConst.DB_PASSWORD);
			Class.forName(driver);
			Connection con = DriverManager.getConnection(jdbcurl, username,
					password==null?"":password);
			String sql = query + "('" + buildNum + "')";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Measure min = new Measure(HarMetrics.RESP_TIME_MIN,
						rs.getDouble("resptimemin"));
				Measure max = new Measure(HarMetrics.RESP_TIME_MAX,
						rs.getDouble("resptimemax"));
				Measure avg = new Measure(HarMetrics.RESP_TIME_AVG,
						rs.getDouble("resptimeavg"));
				Measure stdev = new Measure(HarMetrics.RESP_TIME_STDEV,
						rs.getDouble("resptimestdev"));
				Measure h400 = new Measure(HarMetrics.TOTAL_HTTP400)
						.setIntValue(rs.getInt("http400ct"));
				Measure h500 = new Measure(HarMetrics.TOTAL_HTTP500)
						.setIntValue(rs.getInt("http500ct"));
				Measure ah400 = new Measure(HarMetrics.AVG_HTTP400,
						rs.getDouble("http400avgct"));
				Measure ah500 = new Measure(HarMetrics.AVG_HTTP500,
						rs.getDouble("http500avgct"));
				Measure build = new Measure(HarMetrics.BUILD, buildNum);
				sensorContext.saveMeasure(min);
				sensorContext.saveMeasure(max);
				sensorContext.saveMeasure(avg);
				sensorContext.saveMeasure(stdev);
				sensorContext.saveMeasure(h400);
				sensorContext.saveMeasure(h500);
				sensorContext.saveMeasure(ah400);
				sensorContext.saveMeasure(ah500);
				sensorContext.saveMeasure(build);
			}
			rs.close();
			stmt.close();
			con.close();
		} catch (Exception e) {
			throw new HarPluginException("Importing HAR Data error", e);
		}

	}

	protected void initialize() {
		// Add initialization logic here...
	}

}
