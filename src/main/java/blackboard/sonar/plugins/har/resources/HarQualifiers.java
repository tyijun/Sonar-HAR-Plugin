package blackboard.sonar.plugins.har.resources;

import org.sonar.api.resources.Resource;

public final class HarQualifiers {

	public static String HarQualifier = "HAR";

	private HarQualifiers() {

	}

	public static boolean isHarResource(@SuppressWarnings("rawtypes") Resource resource) {
		if (resource != null && resource.getQualifier().equals(HarQualifier)) {
			return true;
		} else {
			return false;
		}
	}

}
