package me.mattstudios.config.beanmapper;

import me.mattstudios.config.properties.convertresult.ConvertErrorRecorder;
import me.mattstudios.config.utils.TypeInformation;

/**
 * Standard implementation of {@link MappingContext}.
 */
public class MappingContextImpl implements MappingContext {

    private final String path;
    private final TypeInformation typeInformation;
    private final ConvertErrorRecorder errorRecorder;

    protected MappingContextImpl(String path, TypeInformation typeInformation, ConvertErrorRecorder errorRecorder) {
        this.path = path;
        this.typeInformation = typeInformation;
        this.errorRecorder = errorRecorder;
    }

    /**
     * Creates an initial context (used at the start of a mapping process).
     *
     * @param typeInformation the required type
     * @param errorRecorder error recorder to register errors even if a valid value is returned
     * @return root mapping context
     */
    public static MappingContextImpl createRoot(TypeInformation typeInformation, ConvertErrorRecorder errorRecorder) {
        return new MappingContextImpl("", typeInformation, errorRecorder);
    }

    @Override
    public MappingContext createChild(String subPath, TypeInformation typeInformation) {
        if (path.isEmpty()) {
            return new MappingContextImpl(subPath, typeInformation, errorRecorder);
        }
        return new MappingContextImpl(path + "." + subPath, typeInformation, errorRecorder);
    }

    @Override
    public TypeInformation getTypeInformation() {
        return typeInformation;
    }

    @Override
    public String createDescription() {
        return "Path: '" + path + "', type: '" + typeInformation.getType() + "'";
    }

    @Override
    public void registerError(String reason) {
        errorRecorder.setHasError("At path '" + path + "': " + reason);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[" + createDescription() + "]";
    }
}
