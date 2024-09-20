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
                free: freeExecutors
            ]
        }
    }
    
    nodeInfo.each { nodeName, info ->
        echo "Node: ${nodeName}, Total Executors: ${info.total}, Free Executors: ${info.free}"
    }
    
    return nodeInfo
}