
import { useState } from 'react';
import { useNavigate } from 'react-router-dom';

export default function Settings() {
  const navigate = useNavigate();
  const [activeTab, setActiveTab] = useState(3);
  const [notifications, setNotifications] = useState(true);
  const [aiSuggestions, setAiSuggestions] = useState(true);

  const settingsSections = [
    {
      title: 'Hesap',
      items: [
        { icon: 'ri-user-line', label: 'Profil Bilgileri', action: () => {} },
        { icon: 'ri-body-scan-line', label: 'Vücut Ölçüleri', action: () => {} },
        { icon: 'ri-lock-line', label: 'Gizlilik', action: () => {} }
      ]
    },
    {
      title: 'Tercihler',
      items: [
        { icon: 'ri-palette-line', label: 'Stil Tercihleri', action: () => {} },
        { icon: 'ri-t-shirt-line', label: 'Beden Tercihleri', action: () => {} },
        { icon: 'ri-contrast-2-line', label: 'Renk Paleti', action: () => {} }
      ]
    },
    {
      title: 'Uygulama',
      items: [
        { icon: 'ri-notification-line', label: 'Bildirimler', toggle: true, value: notifications, onChange: setNotifications },
        { icon: 'ri-robot-line', label: 'Yapay Zeka Önerileri', toggle: true, value: aiSuggestions, onChange: setAiSuggestions },
        { icon: 'ri-global-line', label: 'Dil', action: () => {} }
      ]
    }
  ];

  return (
    <div className="min-h-screen bg-gradient-to-br from-purple-50 via-pink-50 to-blue-50 pb-20">
      {/* Header */}
      <div className="fixed top-0 left-0 right-0 bg-white/80 backdrop-blur-md z-50 px-5 py-4 shadow-sm">
        <div className="flex items-center gap-3">
          <button 
            onClick={() => navigate('/')}
            className="w-9 h-9 flex items-center justify-center rounded-full hover:bg-gray-100 transition-colors"
          >
            <i className="ri-arrow-left-line text-xl text-gray-700"></i>
          </button>
          <h1 className="text-lg font-bold text-gray-800">Ayarlar</h1>
        </div>
      </div>

      <div className="pt-20 px-5">
        {/* Profile Card */}
        <div className="bg-gradient-to-r from-purple-500 to-pink-500 rounded-3xl p-6 text-white mb-6 shadow-lg">
          <div className="flex items-center gap-4 mb-4">
            <div className="w-16 h-16 rounded-full bg-white/20 flex items-center justify-center">
              <i className="ri-user-line text-3xl"></i>
            </div>
            <div>
              <h2 className="text-xl font-bold mb-1">Kullanıcı Adı</h2>
              <p className="text-sm text-white/80">user@example.com</p>
            </div>
          </div>
          <div className="grid grid-cols-3 gap-3">
            <div className="bg-white/10 rounded-xl p-3 text-center">
              <div className="text-2xl font-bold mb-1">24</div>
              <div className="text-xs text-white/80">Kıyafet</div>
            </div>
            <div className="bg-white/10 rounded-xl p-3 text-center">
              <div className="text-2xl font-bold mb-1">12</div>
              <div className="text-xs text-white/80">Kombin</div>
            </div>
            <div className="bg-white/10 rounded-xl p-3 text-center">
              <div className="text-2xl font-bold mb-1">48</div>
              <div className="text-xs text-white/80">Deneme</div>
            </div>
          </div>
        </div>

        {/* Settings Sections */}
        {settingsSections.map((section, sectionIndex) => (
          <div key={sectionIndex} className="mb-6">
            <h3 className="text-sm font-bold text-gray-500 uppercase tracking-wider mb-3 px-2">
              {section.title}
            </h3>
            <div className="bg-white rounded-3xl overflow-hidden shadow-sm">
              {section.items.map((item, itemIndex) => (
                <div key={itemIndex}>
                  <button
                    onClick={item.action}
                    className="w-full px-5 py-4 flex items-center justify-between hover:bg-gray-50 transition-colors"
                  >
                    <div className="flex items-center gap-3">
                      <div className="w-10 h-10 rounded-xl bg-purple-50 flex items-center justify-center">
                        <i className={`${item.icon} text-purple-600 text-lg`}></i>
                      </div>
                      <span className="font-semibold text-gray-800">{item.label}</span>
                    </div>
                    {item.toggle ? (
                      <button
                        onClick={(e) => {
                          e.stopPropagation();
                          item.onChange?.(!item.value);
                        }}
                        className={`relative w-12 h-7 rounded-full transition-colors ${
                          item.value ? 'bg-purple-600' : 'bg-gray-300'
                        }`}
                      >
                        <div
                          className={`absolute top-1 w-5 h-5 bg-white rounded-full transition-transform ${
                            item.value ? 'translate-x-6' : 'translate-x-1'
                          }`}
                        ></div>
                      </button>
                    ) : (
                      <i className="ri-arrow-right-s-line text-xl text-gray-400"></i>
                    )}
                  </button>
                  {itemIndex < section.items.length - 1 && (
                    <div className="h-px bg-gray-100 mx-5"></div>
                  )}
                </div>
              ))}
            </div>
          </div>
        ))}

        {/* About */}
        <div className="bg-white rounded-3xl p-5 shadow-sm mb-6">
          <h3 className="font-bold text-gray-800 mb-4">Hakkında</h3>
          <div className="space-y-3">
            <button className="w-full flex items-center justify-between py-2">
              <span className="text-sm text-gray-600">Versiyon</span>
              <span className="text-sm font-semibold text-gray-800">1.0.0</span>
            </button>
            <button className="w-full flex items-center justify-between py-2">
              <span className="text-sm text-gray-600">Gizlilik Politikası</span>
              <i className="ri-arrow-right-s-line text-gray-400"></i>
            </button>
            <button className="w-full flex items-center justify-between py-2">
              <span className="text-sm text-gray-600">Kullanım Koşulları</span>
              <i className="ri-arrow-right-s-line text-gray-400"></i>
            </button>
          </div>
        </div>

        {/* Logout */}
        <button className="w-full bg-red-50 text-red-600 py-4 rounded-2xl font-semibold hover:bg-red-100 transition-colors flex items-center justify-center gap-2">
          <i className="ri-logout-box-line text-xl"></i>
          Çıkış Yap
        </button>
      </div>

      {/* Bottom Navigation */}
      <div className="fixed bottom-0 left-0 right-0 bg-white border-t border-gray-100 px-0 py-0 z-50">
        <div className="grid grid-cols-4 h-16">
          {[
            { icon: 'ri-home-5-line', label: 'Ana Sayfa', path: '/' },
            { icon: 'ri-shirt-line', label: 'Gardırop', path: '/wardrobe' },
            { icon: 'ri-palette-line', label: 'Kombinler', path: '/combinations' },
            { icon: 'ri-user-fill', label: 'Profil', path: '/settings' }
          ].map((tab, index) => (
            <button
              key={index}
              onClick={() => {
                setActiveTab(index);
                navigate(tab.path);
              }}
              className={`flex flex-col items-center justify-center gap-1 transition-colors ${
                activeTab === index ? 'text-purple-600' : 'text-gray-400'
              }`}
            >
              <i className={`${tab.icon} text-xl`}></i>
              <span className="text-[0.625rem] font-medium">{tab.label}</span>
            </button>
          ))}
        </div>
      </div>
    </div>
  );
}
