package blackboard.sonar.plugins.har;

import java.util.Arrays;
import java.util.List;

import org.sonar.api.Properties;
import org.sonar.api.Property;
import org.sonar.api.PropertyType;
import org.sonar.api.SonarPlugin;

import blackboard.sonar.plugins.definition.Har;
import blackboard.sonar.plugins.definition.HarMetrics;
import blackboard.sonar.plugins.definition.HarProfile;
import blackboard.sonar.plugins.har.batch.HarDecorator;
import blackboard.sonar.plugins.har.batch.HarPostJob;
import blackboard.sonar.plugins.har.batch.HarSensor;
import blackboard.sonar.plugins.har.ui.HarFooter;
import blackboard.sonar.plugins.har.ui.HarRubyWidget;

/**
 * This class is the entry point for all extensions
 */
@Properties({
		@Property(key = HarPluginConst.DATASOURCE, name = "HAR Analysis DataSource", description = "Getting HAR analysis result data from [ DB | FILE | WEB API ]", defaultValue = "DB", project = true, global = true, type = PropertyType.SINGLE_SELECT_LIST, options = {
				"DB", "FILE", "WEBAPI" }),
		@Property(key = HarPluginConst.FILE_EXTENSIONS, name = "HAR Analysis Result File Extensions", description = "The analysis result file extensions when the HAR Analysis DataSource property is set to FILE ( split by comma )", defaultValue = "har", project = true, global = true, type = PropertyType.STRING),
		@Property(key = HarPluginConst.SOURCE_DIRECTORY, name = "Har Analysis Result File Directory", description = "The analysis result file directory when the HAR Analysis DataSource property is set to FILE", project = true, global = true, type = PropertyType.STRING),
		@Property(key = HarPluginConst.DB_DRIVER, name = "DB Driver", description = "The db driver when the HAR Analysis DataSource property is set to DB", defaultValue = "", project = true, global = true, type = PropertyType.STRING),
		@Property(key = HarPluginConst.DB_JDBCURL, name = "DB JDBC_URL", description = "The db jdbc_url when the HAR Analysis DataSource property is set to DB", defaultValue = "", project = true, global = true, type = PropertyType.STRING),
		@Property(key = HarPluginConst.DB_USERNAME, name = "DB Username", description = "The db username when the HAR Analysis DataSource property is set to DB", defaultValue = "", project = true, global = true, type = PropertyType.STRING),
		@Property(key = HarPluginConst.DB_PASSWORD, name = "DB Password", description = "The db password when the HAR Analysis DataSource property is set to DB", defaultValue = "", project = true, global = true, type = PropertyType.STRING),
		@Property(key = HarPluginConst.SAVE_RESULT_AS_FILE, name = "SAVE RESULT AS FILE", description = "To determin if save the analysis result data as a source file", defaultValue = "false", project = false, global = false, type = PropertyType.BOOLEAN)})
public final class HarPlugin extends SonarPlugin {

	// This is where you're going to declare all your Sonar extensions
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List getExtensions() {
		return Arrays.asList(
		// Definitions
				Har.class, HarMetrics.class,
				// Profile
				HarProfile.class,
				// Batch
				HarSensor.class, HarDecorator.class, HarPostJob.class,
				// UI
				HarFooter.class, HarRubyWidget.class);
	}

	@Override
	public String toString() {
		return getClass().getSimpleName();
	}
}
