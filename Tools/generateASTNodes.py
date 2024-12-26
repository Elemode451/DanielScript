import os

def generateNodeClass(node, name, indent="    "):
    className = node["name"]
    fields = node["fields"]

    classCode = f"{indent}public static class {className} extends " + name + "{\n"

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

    classCode += f"\n{indent}    @Override\n{indent}    public <T> T accept(" + name + f"Visitor<T> visitor) {{\n{indent}        return visitor.visit{className}(this);\n{indent}    }}\n"

    classCode += f"{indent}}}\n"
    return classCode

def generateNodes(nodeTypes, name):
    classCode = "public abstract class " + name + "{\n\n    public abstract <T> T accept(" + name + "Visitor<T> visitor);\n\n"

    classCode += "    public interface " + name + "Visitor<T> {\n"
    for node in nodeTypes:
        className = node["name"]
        classCode += f"        T visit{className}({className} node);\n"
    classCode += "    }\n\n"

    for node in nodeTypes:
        classCode += generateNodeClass(node, name, indent="    ") + "\n"

    classCode += "}"
    with open(os.path.join(os.getcwd(), name + ".java"), "w") as file:
        file.write(classCode)

if __name__ == "__main__":
    exprNodeTypes = [
        {
            "name": "LiteralNode",
            "fields": [
                {"type": "Object", "name": "literal"}
            ]
        },
        {
            "name": "BinaryNode",
            "fields": [
                {"type": "Token", "name": "operator"},
                {"type": "ExprNode", "name": "left"},
                {"type": "ExprNode", "name": "right"}
            ]
        },
        {
            "name": "UnaryNode",
            "fields": [
                {"type": "Token", "name": "operator"},
                {"type": "ExprNode", "name": "operand"}
            ]
        },
        {
            "name": "GroupingNode",
            "fields": [
                {"type": "ExprNode", "name": "expression"}
            ]
        },

    ]
    
    stmtNodeTypes = [
        {
            "name": "PrintNode",
            "fields": [
                {"type": "ExprNode", "name": "expression"}
            ]
        },
        {
            "name": "AssignmentNode",
            "fields": [
                {"type": "String", "name": "variableName"},
                {"type": "ExprNode", "name": "expression"}
            ]
        },
    ]
    generateNodes(exprNodeTypes, "ExprNode")
    generateNodes(stmtNodeTypes, "StatementNode")
