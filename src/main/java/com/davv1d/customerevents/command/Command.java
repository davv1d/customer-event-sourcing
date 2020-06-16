package com.davv1d.customerevents.command;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type", defaultImpl = VoidCommand.class)
@JsonSubTypes({
        @JsonSubTypes.Type(name = "customer.activate", value = ActivateCommand.class),
        @JsonSubTypes.Type(name = "customer.deactivate", value = DeactivateCommand.class),
        @JsonSubTypes.Type(name = "customer.changeName", value = ChangeNameCommand.class),
        @JsonSubTypes.Type(name = "customer.create", value = CreateCommand.class)
})
public interface Command {
}

class VoidCommand implements Command {

}