package blackboard.sonar.plugins.har.importer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.resources.Project;

public abstract class HarDataGenerator {

	public abstract void generateResultFiles();

	private static final Logger LOG = LoggerFactory
			.getLogger(HarDataGenerator.class);
	private Project project;

	public HarDataGenerator(Project project) {
		this.project = project;
	}

	public void convertHarDataToFile(File file, Map<String, String> data) {
		StringBuffer sb = new StringBuffer();
		int i = 0;
		if (data != null && data.size() > 0) {
			for (Map.Entry<String, String> entry : data.entrySet()) {
				if (i > 0) {
					sb.append("\r\n");
				}
				sb.append(entry.getKey() + "=" + entry.getValue());
				i++;
			}
		}
		String content = sb.toString();
		FileOutputStream writer = null;
		try {
			file.createNewFile();
			writer = new FileOutputStream(file);
			writer.write(content.getBytes(project.getFileSystem().getSourceCharset()));
		} catch (IOException e) {
			LOG.error("Saving HAR data to file:" + file.getAbsolutePath()
					+ " error", e);
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e2) {
					// Ignore it.
				}
			}
		}

	}
}
