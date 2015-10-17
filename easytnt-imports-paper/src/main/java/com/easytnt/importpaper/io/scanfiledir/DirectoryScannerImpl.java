/**
 * 
 */
package com.easytnt.importpaper.io.scanfiledir;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * @author liuyu
 *
 */
public class DirectoryScannerImpl implements DirectoryScanner {
	private Path rootPath;
	private FileInfoBulidor buildor;

	public DirectoryScannerImpl(String rootDir) {
		this.rootPath = Paths.get(rootDir);
		buildor = new FileInfoBulidor(rootDir);
	}

	@Override
	public CountContainer<FileInfo> scan() throws IOException {
		final CountContainer<FileInfo> itemImageFileContainer = new CountContainer<>(20);
		SimpleFileVisitor<Path> visitor = new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
				FileInfo itemImageFile = buildor.building(file);
				itemImageFileContainer.add(itemImageFile);
				return super.visitFile(file, attrs);
			}
		};
		Files.walkFileTree(rootPath, visitor);
		return itemImageFileContainer;
	}

	@Override
	public void scan(final VisitorFile visitorFile) throws IOException {
		SimpleFileVisitor<Path> visitor = new SimpleFileVisitor<Path>() {

			@Override
			public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
				FileInfo itemImageFile = buildor.building(file);
				visitorFile.visit(itemImageFile);
				return super.visitFile(file, attrs);
			}

		};

		Files.walkFileTree(rootPath, visitor);
	}

}
