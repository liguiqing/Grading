/**
 * 
 */
package com.easytnt.io.scanfiledir;

/**
 * @author liuyu
 *
 */
public class DirectoryScannerFactory {

	public static DirectoryScanner getDirectoryScanner(String directory) {
		return new DirectoryScannerImpl(directory);
	}
}
