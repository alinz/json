package com.github.alinz.json;

import java.util.ArrayList;
import java.util.HashMap;

public class JsonParser {

    private JsonLexer lex;

    //Grammer
    // JSON -> value
    // value -> Map | Array | Boolean | String | Number | NULL
    // Array -> '[' + value + <',' + value> + ']'hrom
    // Map -> '{' + property + <',' + property> + '}'
    // property -> String + ':' + value

    private Token next() {
        return lex.nextToken();
    }

    private JsonNode.StringNode stringNode(Token token) {
        return new JsonNode.StringNode(token.value);
    }

    private JsonNode.NumberNode numberNode(Token token) {
        return new JsonNode.NumberNode(Double.parseDouble(token.value));
    }

    private JsonNode.NullNode nullNode() {
        return new JsonNode.NullNode();
    }

    private JsonNode.BooleanNode booleanNode(Token token) {
        return new JsonNode.BooleanNode(token.value.equals("true"));
    }

    private JsonNode.MapNode mapNode() {
        JsonNode.MapNode map = new JsonNode.MapNode(new HashMap<String, JsonNode>());

        Token token = next();
        if (token.type == Token.Type.CLOSE_MAP) {
            return map;
        }

        do {
            String key = token.value;
            token = next();
            if (token.type != Token.Type.COLON) {
                throw new Error("Colon must be set after key");
            }

            //discard the colon
            map.value.put(key, value());

            token = next();
            if (token.type == Token.Type.COMMA) {
                //we still have more key to go
                token = next();
            } else if (token.type == Token.Type.CLOSE_MAP) {
                //the map is done.
                break;
            } else {
                throw new Error("this shouldn't happen");
            }
        } while(true);

        return map;
    }

    private JsonNode.ArrayNode arrayNode() {
        JsonNode.ArrayNode array = new JsonNode.ArrayNode(new ArrayList<JsonNode>());

        Token token = next();
        if (token.type == Token.Type.CLOSE_ARRAY) {
            return array;
        }

        do {
            array.value.add(value(token));

            token = next();
            if (token.type == Token.Type.COMMA) {
                token = next();
            } else if (token.type == Token.Type.CLOSE_ARRAY) {
                break;
            } else {
                throw new Error("This shouldn't happen");
            }
        } while(true);

        return array;
    }

    private JsonNode value(Token token) {
        if (token.type == Token.Type.STRING) {
            return stringNode(token);
        } else if (token.type == Token.Type.NUMBER) {
            return numberNode(token);
        } else if (token.type == Token.Type.BOOLEAN) {
            return booleanNode(token);
        } else if (token.type == Token.Type.NULL) {
            return nullNode();
        } else if (token.type == Token.Type.OPEN_MAP) {
            return mapNode();
        } else if (token.type == Token.Type.OPEN_ARRAY) {
            return arrayNode();
        }
        return null;
    }

    private JsonNode value() {
        Token token = next();
        return value(token);
    }

    public JsonParser(String input) {
        lex = new JsonLexer(input);
    }

    public JsonNode parse() {
        return value();
    }


    public static void main(String[] args) {
        JsonParser json = new JsonParser("{ \"name\"     : [ \"Hello world!\", {}, []]}");
        JsonNode node = json.parse();
        System.out.println(node);
    }
}
