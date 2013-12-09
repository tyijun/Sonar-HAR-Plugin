package blackboard.sonar.plugins.har.resources;

import java.io.File;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.sonar.api.resources.Project;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

public class HarFile extends HarResource {

	private String filename;

	public HarFile(String key) {
		super(key);
		if (key == null) {
			throw new IllegalArgumentException("File key is null");
		}
		key = key.replace('\\', '/');
		String realKey = StringUtils.trim(key);
		if (realKey.indexOf('/') >= 0) {
			this.filename = StringUtils.substringAfterLast(realKey, "/");
		} else {
			this.filename = realKey;
		}
		setKey(realKey);
	}

	public static HarFile fromIOFile(java.io.File file,
			List<java.io.File> sourceDirs) {
		String relativePath = relativePath(sourceDirs, file);
		if (relativePath != null) {
			return new HarFile(relativePath);
		}
		return null;
	}

	public static HarFile fromIOFile(java.io.File file, Project project) {
		return fromIOFile(file, project.getFileSystem().getSourceDirs());
	}

	@Override
	public String getName() {
		return filename;
	}

	private static String relativePath(Collection<File> dirs, File file) {
		List<String> stack = Lists.newArrayList();
		String path = FilenameUtils.normalize(file.getAbsolutePath());
		File cursor = new File(path);
		while (cursor != null) {
			File parentDir = parentDir(dirs, cursor);
			if (parentDir != null) {
				return Joiner.on("/").join(stack);
			}
			stack.add(0, cursor.getName());
			cursor = cursor.getParentFile();
		}
		return null;
	}

	private static File parentDir(Collection<File> dirs, File cursor) {
		for (File dir : dirs) {
			if (FilenameUtils.equalsNormalizedOnSystem(dir.getAbsolutePath(),
					cursor.getAbsolutePath())) {
				return dir;
			}
		}
		return null;
	}

}
