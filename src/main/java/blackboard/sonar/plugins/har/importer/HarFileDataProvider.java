package blackboard.sonar.plugins.har.importer;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.config.Settings;
import org.sonar.api.measures.Measure;
import org.sonar.api.resources.Project;

import blackboard.sonar.plugins.definition.Har;
import blackboard.sonar.plugins.har.HarPluginConst;
import blackboard.sonar.plugins.har.resources.HarFile;
import blackboard.sonar.plugins.har.resources.HarResource;

public class HarFileDataProvider implements HarDataProvider {

	private static final Logger LOG = LoggerFactory
			.getLogger(HarFileDataProvider.class);
	private Project project;
	private Settings settings;

	public HarFileDataProvider(Settings settings, Project project) {
		this.settings = settings;
		this.project = project;
	}

	@SuppressWarnings("deprecation")
	public Map<HarResource, List<Measure>> buildData() {
		// TODO Auto-generated method stub
		Map<HarResource, List<Measure>> data = new HashMap<HarResource, List<Measure>>();
		try {
		//	configureSourceDir();
			for (java.io.File harFile : project.getFileSystem().getSourceFiles(
					new Har(settings))) {
				try {
					HarFile resource = HarFile.fromIOFile(harFile, project
							.getFileSystem().getSourceDirs());
					List<Measure> measures = parseHarFile(harFile);
					data.put(resource, measures);
				} catch (Exception e) {
					LOG.error(
							"Could not analyze the har file"
									+ harFile.getAbsolutePath(), e);
				}
			}
		} catch (Exception ex) {
			LOG.error("Error when building data:" + ex.getLocalizedMessage());
		}
		return data;
	}

	@SuppressWarnings("deprecation")
	protected void configureSourceDir() {
		String sourceDir = settings.getString(HarPluginConst.SOURCE_DIRECTORY);
		if (sourceDir != null) {
			File file = new File(project.getFileSystem().getBasedir() + "/"
					+ sourceDir);
	//		project.getPom().getCompileSourceRoots().clear();
			project.getFileSystem().addSourceDir(file);
		}
	}

	protected List<Measure> parseHarFile(File harFile) {
		return null;
	}

}
