/**
 * 
 */
package com.easytnt.uploditemiamge.scanfiledir;

import java.io.IOException;

/**
 * @author T440P
 *
 */
public interface DirectoryScanner {
	public FileInfoContainer scan() throws IOException;

	public void scan(VisitorFile visitorFile) throws IOException;
}
