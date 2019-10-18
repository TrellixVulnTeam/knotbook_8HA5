package kb.core.application

import kb.service.api.Service
import kb.service.api.ServiceContext
import kb.service.api.ServiceProps
import kb.service.api.ui.CommandManager
import kb.service.api.ui.OptionBar
import kb.service.api.ui.TextEditor

class ServiceContextImpl(
        private val service: Service,
        private val manager: ServiceManagerImpl
) : ServiceContext {
    override fun getService(): Service {
        return service
    }

    override fun getProps(): ServiceProps {
        return manager.props.getProps(service.metadata.packageName)
    }

    override fun createTextEditor(): TextEditor {
        return manager.createTextEditor()
    }

    override fun getOptionBar(): OptionBar {
        TODO("not implemented")
    }

    override fun getCommandManager(): CommandManager {
        TODO("not implemented")
    }
}