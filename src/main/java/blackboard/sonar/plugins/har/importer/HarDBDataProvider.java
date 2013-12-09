package blackboard.sonar.plugins.har.importer;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.measures.Measure;

import blackboard.sonar.plugins.definition.HarMetrics;
import blackboard.sonar.plugins.har.resources.HarResource;

public class HarDBDataProvider implements HarDataProvider {

	private static final Logger LOG = LoggerFactory
			.getLogger(HarDBDataProvider.class);
	private String driver;
	private String jdbcurl;
	private String username;
	private String password;
	private String query = "select * from har.getallmetrics('9.1.160062.0');";

	public HarDBDataProvider(String driver, String jdbcurl, String username,
			String password) {
		this.driver = driver;
		this.jdbcurl = jdbcurl;
		this.username = username;
		this.password = password;
	}

	public Map<HarResource, List<Measure>> buildData() {
		// TODO Auto-generated method stub
		Map<HarResource, List<Measure>> data = new HashMap<HarResource, List<Measure>>();
		try {
			/*
			 * Class.forName(driver); Connection con =
			 * DriverManager.getConnection(jdbcurl, username, password);
			 * Statement stmt = con.createStatement(); ResultSet rs =
			 * stmt.executeQuery(query); ResultSetMetaData meta =
			 * rs.getMetaData(); List<String> colNames = new
			 * ArrayList<String>(); List<String> colTypes = new
			 * ArrayList<String>(); for (int i = 0; i < meta.getColumnCount();
			 * i++) { colNames.add(meta.getColumnLabel(i + 1));
			 * colTypes.add(meta.getColumnTypeName(i + 1)); } while (rs.next())
			 * { for (int i = 0; i < meta.getColumnCount(); i++) { String info =
			 * rs.getString(i + 1); String name = meta.getColumnLabel(i + 1);
			 * System.out.println(name + ":" + info); } } rs.close();
			 * stmt.close(); con.close();
			 */		
			Date date = new Date();			
			for(int i=0; i<25;i++){
				int random = 1 + (int) (Math.random() * 100);
				Measure min = new Measure(HarMetrics.RESP_TIME_MIN, 1.1*random);
				Measure max = new Measure(HarMetrics.RESP_TIME_MAX, 12.5*random);
				Measure avg = new Measure(HarMetrics.RESP_TIME_AVG, 2.5*random);
				Measure stdev = new Measure(HarMetrics.RESP_TIME_STDEV, 3.5*random);
				Measure h400 = new Measure(HarMetrics.TOTAL_HTTP400)
						.setIntValue(100*random);
				Measure h500 = new Measure(HarMetrics.TOTAL_HTTP500)
						.setIntValue(120*random);
				Measure ah400 = new Measure(HarMetrics.AVG_HTTP400).setIntValue(60*random);
				Measure ah500 = new Measure(HarMetrics.AVG_HTTP500).setIntValue(80*random);
				Measure build = new Measure(HarMetrics.BUILD, "9.1.160062.0");
				Measure importAt = new Measure(HarMetrics.IMPORT_TIME,
						date.toString());			
				List<Measure> list = new ArrayList<Measure>();
				list.add(min);
				list.add(max);
				list.add(avg);
				list.add(stdev);
				list.add(h400);
				list.add(h500);
				list.add(ah400);
				list.add(ah500);
				list.add(build);
				list.add(importAt);
				String nkey = "HAR_" + date.getTime() + "_" + (i+1);
				HarResource resource = new HarResource(nkey, "HAR Analysis Result");
				data.put(resource, list);
			}			
		} catch (Exception ex) {
			LOG.error("Error when building data from DB:", ex);
		}
		return data;
	}

}