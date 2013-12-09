package blackboard.sonar.plugins.definition;

import org.sonar.api.config.Settings;
import org.sonar.api.resources.AbstractLanguage;

import blackboard.sonar.plugins.har.HarPluginConst;

public class Har extends AbstractLanguage {

	// All the valid har analysis result files suffixes
	private static final String[] DEFAULT_SUFFIXES = { "har" };

	// A Har instance
	public static final Har INSTANCE = new Har();

	// The har language key
	public static final String KEY = "har";

	/** The har language name */
	private static final String HAR_LANGUAGE_NAME = "HAR";

	private String[] fileSuffixes;

	/**
	 * Default constructor.
	 */
	public Har() {
		super(KEY, HAR_LANGUAGE_NAME);
	}

	public Har(Settings configs) {
		this();
		if (configs.getString(HarPluginConst.FILE_EXTENSIONS) != null) {
			String[] extensions = configs
					.getStringArray(HarPluginConst.FILE_EXTENSIONS);
			if (extensions != null && extensions.length > 0) {
				fileSuffixes = new String[extensions.length];
				for (int i = 0; i < extensions.length; i++) {
					fileSuffixes[i] = extensions[i];
				}
			}
		}
	}

	/**
	 * Gets the file suffixes.
	 * 
	 * @return the file suffixes
	 * @see org.sonar.api.resources.Language#getFileSuffixes()
	 */
	public String[] getFileSuffixes() {
		return fileSuffixes == null ? DEFAULT_SUFFIXES : fileSuffixes;
	}
}
