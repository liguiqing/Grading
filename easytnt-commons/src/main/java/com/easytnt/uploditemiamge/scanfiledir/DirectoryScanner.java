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
	public ItemImageFileContainer scan() throws IOException;

	public void scan(VisitorFile visitorFile) throws IOException;
}
