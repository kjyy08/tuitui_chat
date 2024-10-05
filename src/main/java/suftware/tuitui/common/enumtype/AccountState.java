package suftware.tuitui.common.enumtype;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AccountState {
    ACTIVE("ACTIVE"),
    INACTIVE("INACTIVE"),
    DELETED("DELETED");

    private final String value;
}
