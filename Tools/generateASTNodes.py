import os

def generateNodeClass(node, indent="    "):
    className = node["name"]
    fields = node["fields"]

    classCode = f"{indent}public static class {className} extends ASTNode {{\n"

    for field in fields:
        classCode += f"{indent}    private final {field['type']} {field['name']};\n"

    classCode += f"\n{indent}    public {className}("
    classCode += ", ".join([f"{field['type']} {field['name']}" for field in fields])
    classCode += f") {{\n"
    for field in fields:
        classCode += f"{indent}        this.{field['name']} = {field['name']};\n"
    classCode += f"{indent}    }}\n"

    for field in fields:
        classCode += f"\n{indent}    public {field['type']} get{field['name'][0].upper() + field['name'][1:]}() {{\n"
        classCode += f"{indent}        return {field['name']};\n"
        classCode += f"{indent}    }}\n"

    classCode += f"\n{indent}    @Override\n{indent}    public <T> T accept(ASTNodeVisitor<T> visitor) {{\n{indent}        return visitor.visit{className}(this);\n{indent}    }}\n"

    classCode += f"{indent}}}\n"
    return classCode

def generateNodes(nodeTypes):
    classCode = "public abstract class ASTNode {\n\n    public abstract <T> T accept(ASTNodeVisitor<T> visitor);\n\n"

    classCode += "    public interface ASTNodeVisitor<T> {\n"
    for node in nodeTypes:
        className = node["name"]
        classCode += f"        T visit{className}({className} node);\n"
    classCode += "    }\n\n"

    for node in nodeTypes:
        classCode += generateNodeClass(node, indent="    ") + "\n"

    classCode += "}"
    with open(os.path.join(os.getcwd(), "ASTNode.java"), "w") as file:
        file.write(classCode)

if __name__ == "__main__":
    nodeTypes = [
        {
            "name": "LiteralNode",
            "fields": [
                {"type": "Object", "name": "value"}
            ]
        },
        {
            "name": "BinaryNode",
            "fields": [
                {"type": "Token", "name": "operator"},
                {"type": "ASTNode", "name": "left"},
                {"type": "ASTNode", "name": "right"}
            ]
        },
        {
            "name": "UnaryNode",
            "fields": [
                {"type": "Token", "name": "operator"},
                {"type": "ASTNode", "name": "operand"}
            ]
        },
        {
            "name": "IdentifierNode",
            "fields": [
                {"type": "String", "name": "name"}
            ]
        }
    ]

    generateNodes(nodeTypes)
