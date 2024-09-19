def call() {
    def hostNames("windows") {
        def nodes = []
        jenkins.model.Jenkins.get().computers.each { c ->
            if (c.node.labelString.contains(label)) {
            nodes.add(c.node.selfLabel.name)
            }
        }
        return nodes
    }
}