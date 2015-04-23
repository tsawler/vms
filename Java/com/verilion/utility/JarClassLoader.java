//------------------------------------------------------------------------------
//Copyright (c) 2003 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2005-04-27
//Revisions
//------------------------------------------------------------------------------
//$Log: JarClassLoader.java,v $
//Revision 1.1.2.4  2005/05/03 12:27:00  tcs
//Added support for modules with more than one class
//
//Revision 1.1.2.3  2005/05/02 17:01:34  tcs
//Complete and functional
//
//Revision 1.1.2.2  2005/04/29 13:28:34  tcs
//In progress...
//
//Revision 1.1.2.1  2005/04/27 18:10:33  tcs
//Initial entry
//
//------------------------------------------------------------------------------
package com.verilion.utility;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import com.verilion.database.SingletonObjects;

/**
 * Class loader. Pass it a file and the name of a class, and it will allow you
 * to make a class out of a byte array loaded from a jar file.
 * 
 * @author tcs
 */
public class JarClassLoader extends ClassLoader {

	String[] dirs;
	String jarFile = "";
	ClassLoader parent = this.getClass().getClassLoader();

	public JarClassLoader() {

	}

	public JarClassLoader(String path) {
		dirs = path.split(System.getProperty("path.separator"));
	}

	public JarClassLoader(String path, ClassLoader parent) {
		super(parent);
		dirs = path.split(System.getProperty("path.separator"));
		this.extendClassPath(path);
	}

	public JarClassLoader(String path, ClassLoader parent, String jarfile) {
		super(parent);
		dirs = path.split(System.getProperty("path.separator"));
		jarFile = jarfile;
	}

	/**
	 * Extend classpath to user specified directory
	 * 
	 * @param path
	 */
	public void extendClassPath(String path) {
		String[] exDirs = path.split(System.getProperty("path.separator"));
		String[] newDirs = new String[dirs.length + exDirs.length];
		System.arraycopy(dirs, 0, newDirs, 0, dirs.length);
		System.arraycopy(exDirs, 0, newDirs, 0, exDirs.length);
		dirs = newDirs;
	}

	/**
	 * Get class data as byte array from jar file
	 * 
	 * @param theDir
	 * @param name
	 * @param jarName
	 * @return byte[]
	 * @throws IOException
	 */
	public byte[] getClassData(String theDir, String name, String jarName)
			throws IOException {
		String classFile = SingletonObjects.getInstance().getSystem_path()
				+ "UserJars/" + jarName;

		byte[] byteArray = null;
		JarFile jf = new JarFile((new File(classFile)).getAbsoluteFile());
		String realName = name.replace('.', '/');
		realName += ".class";
		JarEntry entry = jf.getJarEntry(realName);

		if (entry != null) {
			try {
				byteArray = new byte[(int) entry.getSize()];
				BufferedInputStream is = new BufferedInputStream(
						jf.getInputStream(entry));
				is.read(byteArray);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return byteArray;
	}

	/**
	 * Get and load all class data from specified jar file
	 * 
	 * @param jarName
	 * @throws IOException
	 */
	public synchronized void LoadAllClassDataFromJar(String jarName)
			throws IOException {

		String theJarFile = SingletonObjects.getInstance().getSystem_path()
				+ "UserJars/" + jarName;
		byte[] byteArray = null;
		ZipFile zip = new ZipFile(theJarFile);
		JarFile jf = new JarFile((new File(theJarFile)).getAbsoluteFile());

		try {
			// Loop through the zip entries and load each one.
			for (Enumeration list = zip.entries(); list.hasMoreElements();) {
				ZipEntry entry = (ZipEntry) list.nextElement();
				if ((entry.toString()).endsWith(".class")) {
					JarEntry jEntry = jf.getJarEntry(entry.toString());
					if (jEntry != null) {
						try {
							byteArray = new byte[(int) jEntry.getSize()];
							BufferedInputStream is = new BufferedInputStream(
									jf.getInputStream(jEntry));
							is.read(byteArray);
							StringBuffer sb = new StringBuffer(entry.toString());
							sb.delete((sb.length() - 6), sb.length());
							defineClass(sb.toString(), byteArray, 0,
									byteArray.length);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		} finally {
			zip.close();
		}
	}

	/**
	 * Get all class data for a module from a jar, load the classes, and return
	 * the class specified as a parameter (the entry class for a module)
	 * 
	 * @param jarName
	 * @param className
	 * @return Class
	 * @throws IOException
	 */
	public synchronized Class LoadModuleClassDataFromJar(String jarName,
			String className) throws IOException {

		Class myClass = null;
		String theJarFile = SingletonObjects.getInstance().getSystem_path()
				+ "UserJars/" + jarName;
		byte[] byteArray = null;
		ZipFile zip = new ZipFile(theJarFile);
		JarFile jf = new JarFile((new File(theJarFile)).getAbsoluteFile());

		try {
			// Loop through the zip entries and load each one.
			for (Enumeration list = zip.entries(); list.hasMoreElements();) {
				ZipEntry entry = (ZipEntry) list.nextElement();
				if ((entry.toString()).endsWith(".class")) {
					JarEntry jEntry = jf.getJarEntry(entry.toString());
					if (jEntry != null) {
						try {
							byteArray = new byte[(int) jEntry.getSize()];
							BufferedInputStream is = new BufferedInputStream(
									jf.getInputStream(jEntry));
							is.read(byteArray);
							StringBuffer sb = new StringBuffer(entry.toString());
							sb.delete((sb.length() - 6), sb.length());
							String theClass = sb.toString();
							String realName = theClass.replace('/', '.');
							if (realName.equals(className)) {
								myClass = defineClass(sb.toString(), byteArray,
										0, byteArray.length);
							} else {
								defineClass(sb.toString(), byteArray, 0,
										byteArray.length);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		} finally {
			zip.close();
		}
		return myClass;
	}

	/**
	 * Get a single class from a jar file, load it, and return it.
	 * 
	 * @param name
	 * @param jarName
	 * @return Class
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public synchronized Class LoadClass(String name, String jarName)
			throws ClassNotFoundException, IOException {
		byte[] buf = getClassData(SingletonObjects.getInstance()
				.getSystem_path() + "UserJars/", name, jarName);
		if (buf != null)
			return defineClass(name, buf, 0, buf.length);
		throw new ClassNotFoundException();
	}

}