package org.cloudme.example;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

class FlickrAccountDeserializer extends JsonDeserializer<FlickrAccount> {
    @Override
    public FlickrAccount deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException,
            JsonProcessingException {
        FlickrAccount account = new FlickrAccount();
        JsonNode node = jp.readValueAsTree();
        JsonNode user = node.get("user");
        account.setId(user.get("id").asText());
        account.setUsername(user.get("username").get("_content").asText());
        return account;
    }
}
