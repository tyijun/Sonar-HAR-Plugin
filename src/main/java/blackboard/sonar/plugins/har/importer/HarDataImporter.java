package blackboard.sonar.plugins.har.importer;

import blackboard.sonar.plugins.har.exception.HarPluginException;

public interface HarDataImporter {
	
	public void importData() throws HarPluginException;

}
