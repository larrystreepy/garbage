package com.bluehub.util;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * An extension to the standard Spring property configuration class to support integration with the GCS metadata
 * server to tweak the actual name of the property retrieved.  Any original property name that contains the string
 * "metadata.XXX" will have that fragment replaced with the value of the XXX attribute pulled from the metadata for the
 * GCS instance. For example, if a property named "datasource.metadata.STAGE.url" is requested, the value of the
 * "STAGE" attribute will be read from the metadata server and that value will replace the "metadata.STAGE" fragment.
 * Assuming the STAGE attribute has the value "PRODUCTION" the resulting final property name is
 * "datasource.PRODUCTION.url" which will then be used to access the properties defined in the source properties file.
 */
public class MetaDataBackedPlaceholderConfigurer extends PropertyPlaceholderConfigurer {
    private static final Logger logger = Logger.getLogger(MetaDataBackedPlaceholderConfigurer.class);

    private final Pattern mdPattern = Pattern.compile("metadata\\.([^\\.]+)");
    private static final Map<String, String> defaults = new HashMap<>();

    static {
        // Initialize our default values
        defaults.put("STAGE", "DEVELOPMENT");
    }

    /**
     * Resolve the property after pre-processing it to replace all "metadata.XXX" fragments.
     *
     * @param placeholder the placeholder to resolve
     * @param props       the merged properties of this configurer
     * @return the resolved value, of <code>null</code> if none
     */
    @Override
    protected String resolvePlaceholder(String placeholder, Properties props) {

        // Loop and replace all metadata.XXX fragments with the value of the metadata attribute XXX
        final Matcher m = mdPattern.matcher(placeholder);
        final StringBuffer sb = new StringBuffer();

        while (m.find()) {
            final String attrName = m.group(1);
            final String defaultValue = defaults.get(attrName);

            if (defaultValue == null) {
                logger.warn("No default value defined for MD attr '" + attrName + "'");
            }

            // Get the value of the attribute from the metadata server
            final String attrValue = ServerMetaData.getAttribute(attrName, defaultValue);

            m.appendReplacement(sb, Matcher.quoteReplacement(attrValue));
        }

        m.appendTail(sb);

        final String actualPropertyName = sb.toString();

        if (logger.isDebugEnabled()) logger.debug("Property '" + placeholder + "' ==> " + actualPropertyName);

        return super.resolvePlaceholder(actualPropertyName, props);
    }
}
