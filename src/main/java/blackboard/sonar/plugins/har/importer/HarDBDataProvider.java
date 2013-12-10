package blackboard.sonar.plugins.har.importer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.measures.Measure;

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
			Class.forName(driver);
			Connection con = DriverManager.getConnection(jdbcurl, username,
					password);
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			ResultSetMetaData meta = rs.getMetaData();
			List<String> colNames = new ArrayList<String>();
			List<String> colTypes = new ArrayList<String>();
			for (int i = 0; i < meta.getColumnCount(); i++) {
				colNames.add(meta.getColumnLabel(i + 1));
				colTypes.add(meta.getColumnTypeName(i + 1));
			}
			while (rs.next()) {
				for (int i = 0; i < meta.getColumnCount(); i++) {
					String info = rs.getString(i + 1);
					String name = meta.getColumnLabel(i + 1);
					System.out.println(name + ":" + info);
				}
			}
			rs.close();
			stmt.close();
			con.close();			
		} catch (Exception ex) {
			LOG.error("Error when building data from DB:", ex);
		}
		return data;
	}

}