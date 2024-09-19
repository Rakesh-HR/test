def call() {
    def nodes = []
    jenkins.model.Jenkins.get().computers.each { c ->
        if (c.node.labelString.contains("windows")) {
        nodes.add(c.node.selfLabel.name)
        }
    }
    return nodes
}