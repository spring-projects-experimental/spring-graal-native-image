package org.springframework.context.bootstrap.generator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.lang.model.element.Modifier;

import com.squareup.javapoet.JavaFile;

/**
 * Context for components that write code to boostrap the context.
 *
 * @author Stephane Nicoll
 */
public class BootstrapWriterContext {

	static final String BOOTSTRAP_CLASS_NAME = "ContextBootstrapInitializer";

	private final String packageName;

	private final Map<String, BootstrapClass> bootstrapClasses = new HashMap<>();

	BootstrapWriterContext(BootstrapClass defaultJavaFile) {
		this.packageName = defaultJavaFile.getClassName().packageName();
		this.bootstrapClasses.put(packageName, defaultJavaFile);
	}

	/**
	 * Return the package name in which the main bootstrap class is located.
	 * @return the default package name
	 */
	public String getPackageName() {
		return this.packageName;
	}

	/**
	 * Return a {@link BootstrapClass} for the specified package name. If it does not
	 * exist, it is created
	 * @param packageName the package name to use
	 * @return the bootstrap class
	 */
	public BootstrapClass getBootstrapClass(String packageName) {
		return this.bootstrapClasses.computeIfAbsent(packageName, (p) ->
				new BootstrapClass(packageName, BOOTSTRAP_CLASS_NAME, (type) -> type.addModifiers(Modifier.PUBLIC, Modifier.FINAL)));
	}

	/**
	 * Return the list of {@link JavaFile} of known bootstrap classes.
	 * @return the java files of bootstrap classes in this instance
	 */
	public List<JavaFile> toJavaFiles() {
		return this.bootstrapClasses.values().stream().map(BootstrapClass::build).collect(Collectors.toList());
	}

}
