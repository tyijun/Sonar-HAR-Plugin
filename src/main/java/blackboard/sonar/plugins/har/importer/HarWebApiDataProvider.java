package blackboard.sonar.plugins.har.importer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.measures.Measure;

import blackboard.sonar.plugins.har.resources.HarResource;

public class HarWebApiDataProvider implements HarDataProvider {

	private static final Logger LOG = LoggerFactory
			.getLogger(HarWebApiDataProvider.class);

	public HarWebApiDataProvider() {

	}

	public Map<HarResource, List<Measure>> buildData() {
		// TODO Auto-generated method stub
		Map<HarResource, List<Measure>> data = new HashMap<HarResource, List<Measure>>();
		try {
			// add execution logic here...
		} catch (Exception ex) {
			LOG.error("Error when building data from DB:", ex);
		}
		return data;
	}

}
