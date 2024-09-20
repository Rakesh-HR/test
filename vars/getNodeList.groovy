// def call() {
//     def nodes = []
//     jenkins.model.Jenkins.get().computers.each { c ->
//         if (c.node.labelString.contains("windows")) {
//         nodes.add(c.node.selfLabel.name)
//         }
//     }
//     sh "echo $nodes"
//     return nodes
// }

def call() {
    def nodeInfo = [:]
    jenkins.model.Jenkins.get().computers.each { c ->
        if (c.node.labelString.contains("windows")) {
            def nodeName = c.node.selfLabel.name
            def totalExecutors = c.numExecutors
            def busyExecutors = c.countBusy()
            def freeExecutors = totalExecutors - busyExecutors
            
            nodeInfo[nodeName] = [
                total: totalExecutors,
                free: freeExecutors,
                utilization: busyExecutors/totalExecutors*100
            ]
        }
    }
    
    nodeInfo = nodeInfo.sort { a, b -> a.value.utilization <=> b.value.utilization }

    nodeInfo.each { nodeName, info ->
        echo "Node: ${nodeName}, Utilization: ${info.utilization}%"
    }
    
    return nodeInfo
}