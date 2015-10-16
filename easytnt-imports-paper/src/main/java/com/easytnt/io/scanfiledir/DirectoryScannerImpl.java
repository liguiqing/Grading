/**
 * 
 */
package com.easytnt.io.scanfiledir;

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
	public FileInfoContainer scan() throws IOException {

		final FileInfoContainer itemImageFileContainer = new FileInfoContainer();

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
